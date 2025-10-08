package com.neu.system.ai;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.neu.system.ai.config.OllamaConfig;
import com.neu.system.ai.data.ScheduleResult;
import com.neu.system.ai.data.WeatherData;
import com.neu.system.domain.AcwChatMessages;
import com.neu.system.service.IAcwChatMessagesService;
import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.RoleNotFoundException;
import io.github.ollama4j.models.chat.OllamaChatMessage;
import io.github.ollama4j.models.chat.OllamaChatMessageRole;
import io.github.ollama4j.models.chat.OllamaChatRequest;
import io.github.ollama4j.models.chat.OllamaChatRequestBuilder;
import io.github.ollama4j.models.generate.OllamaStreamHandler;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.utils.Options;
import io.github.ollama4j.utils.OptionsBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 生命体征诊断服务
 */
@Service
@Slf4j
public class AgricultureAIService {

    @Autowired
    private OllamaAPI ollama;
    @Autowired
    private OllamaConfig ollamaConfig;

    private Options options;

    @Autowired
    private IAcwChatMessagesService chatMessagesService;

    private static Map<Long, SseEmitter> sseCache = new HashMap<>();

    /**
     * 初始化
     */
    @PostConstruct
    public void init() {
        options = new OptionsBuilder()
                //.setTemperature(0.0f)
                //.setNumPredict(500)
                //.setRepeatPenalty(2.0f)
                .build();
    }

    /**
     * 获取排风计划
     *
     * @param weatherData
     * @param crops
     * @return
     */
    public ScheduleResult getExhauseSchedule(WeatherData.LiveWeather weatherData, String crops) {
        try {
            // 发送请求
            String prompt = PromptUtils.buildSchedulePrompt(weatherData, crops);
            log.debug("请求提示词：\n" + prompt);
            OllamaResult answer = ollama.generate(ollamaConfig.getModelName(), prompt, false, options);
            String resp = answer.getResponse();
            log.debug("原始响应：\n" + resp);
            return parseResponse(resp);
        } catch (Exception e) {
            throw new RuntimeException("API调用失败", e);
        }
    }

    public SseEmitter createChatStream(Long userId, String question) {
        log.info("创建AI会话，用户：{}，问题：{}", userId, question);
        SseEmitter emitter = new SseEmitter(300000L);
        new Thread(() -> {
            try {
                // 获取历史记录
                List<OllamaChatMessage> context = getContext(userId);

                OllamaChatRequestBuilder builder = OllamaChatRequestBuilder.getInstance(ollamaConfig.getModelName());
                builder.withMessage(OllamaChatMessageRole.SYSTEM, PromptUtils.buildConsultPrompt(context));
                if (!context.isEmpty()) {
                    builder.withMessages(context);
                }
                builder.withMessage(OllamaChatMessageRole.USER, question);
                OllamaChatRequest request = builder.build();
                AtomicInteger start = new AtomicInteger(0);
                AtomicInteger end = new AtomicInteger(0);
                AtomicReference<String> reply = new AtomicReference<>("");
                OllamaStreamHandler streamHandler = (s) -> {
                    try {
                        start.set(s.indexOf("<think>"));
                        end.set(s.indexOf("</think>"));
                        if (start.get() >= 0 && end.get() < 0) {
//                            log.info("AI思考中....\n{}", s);
                        } else if (end.get() >= 0) {
                            reply.set(s.substring(end.get() + 8).replaceAll("[\r\n]+", ""));
//                            log.info("AI回复：\n" + reply.get());
                            emitter.send(reply.get() + "\n\n");
                        } else {
                            reply.set(s.replaceAll("[\r\n]+", "</br>"));
//                            log.info("AI回复：\n" + s);
                            emitter.send(s + "\n\n");
                        }
                    } catch (IOException e) {
                        log.error("SSE发送失败", e);
                        emitter.completeWithError(e);
                    }
                };
                ollama.chat(request, streamHandler);

                log.info("保存AI回复：\n" + reply.get());

                // 保存消息
                AcwChatMessages assistantMsg = new AcwChatMessages();
                assistantMsg.setUserId(userId);
                assistantMsg.setRole(OllamaChatMessageRole.ASSISTANT.getRoleName());
                assistantMsg.setContent(reply.get());
                chatMessagesService.insertAcwChatMessages(assistantMsg);
                emitter.send("<EOF>");
                emitter.complete();
            } catch (Exception ex) {
                emitter.completeWithError(ex);
            }
        }).start();

        return emitter;
    }

    // 获取上下文（最近5条）
    private List<OllamaChatMessage> getContext(Long userId) {
        List<OllamaChatMessage> context = new ArrayList<>();
        AcwChatMessages filter = new AcwChatMessages();
        filter.setUserId(userId);
        PageHelper.startPage(1, 5, "create_time desc");
        List<AcwChatMessages> acwChatMessages = chatMessagesService.selectAcwChatMessagesList(filter);
        Collections.reverse(acwChatMessages);
        for (AcwChatMessages msg : acwChatMessages) {
            try {
                OllamaChatMessage ollamaChatMessage = new OllamaChatMessage();
                OllamaChatMessageRole role = OllamaChatMessageRole.getRole(msg.getRole());
                ollamaChatMessage.setRole(role);
                ollamaChatMessage.setContent(msg.getContent());
                context.add(ollamaChatMessage);
            } catch (RoleNotFoundException e) {
                log.warn("角色不存在：{}", msg.getRole());
            }
        }
        return context;
    }

    /**
     * 解析返回结果
     *
     * @param response
     * @return
     */
    private ScheduleResult parseResponse(String response) {
        try {
            // 提取JSON内容
            Pattern pattern = Pattern.compile("```json(.*?)```", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(response);
            if (!matcher.find()) {
                throw new RuntimeException("未找到JSON内容");
            }
            String jsonStr = matcher.group(1).trim();
            log.info("提取JSON内容：\n" + jsonStr);
            JSONObject json = JSON.parseObject(jsonStr);
            ScheduleResult result = new ScheduleResult();
            result.toObject(json);
            return result;
        } catch (Exception e) {
            throw new RuntimeException("响应解析失败: " + response, e);
        }
    }
}
