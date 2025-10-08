package com.neu.system.ai;

import com.neu.system.ai.data.WeatherData;
import io.github.ollama4j.models.chat.OllamaChatMessage;

import java.util.List;

/**
 * 提示词工具
 */
public class PromptUtils {

    /**
     * 构建排风计划提示词
     *
     * @param weatherData 当前天气数据
     * @param crops       种植作物名称
     * @return 构建好的提示词字符串
     */
    public static String buildSchedulePrompt(WeatherData.LiveWeather weatherData, String crops) {
        return "";
//                "
//                你是一个专业的农业环境工程师，请根据以下输入数据生成温室大棚排风计划表：
//
//                天气情况：温度%s°C，湿度%s%%，风力%s级%s风，天气现象：%s
//                种植作物：%s
//
//                请严格按照指定JSON格式输出，不包含任何额外说明。计划表需考虑温度调控、湿度管理、风力利用、天气现象应对和不同作物需求差异。
//                JSON示例格式：
//                {
//                    "reason": "xxxx", // 作物适宜温度分析以及温度控制报告
//                    "schedules":
//                    [
//                      {
//                        "startTime": "HH:mm",  // 排风开始时间
//                        "duration": 60,              // 实际排风时长(分钟)
//                      },
//                      ......
//                    ]
//                }
//                ""
//                .formatted(
//                weatherData.getTemperature(),
//                weatherData.getHumidity(),
//                weatherData.getWindpower(),
//                weatherData.getWinddirection(),
//                weatherData.getWeather(),
//                crops
//        );
    }

    /**
     * 构建咨询提示词
     *
     * @return
     */
    public static String buildConsultPrompt(List<OllamaChatMessage> context) {
        StringBuilder sb = new StringBuilder(""
//                "
//                # 角色
//                您是专注于农业领域的智能助手，请按照以下规则并结合历史对话回答用户提出的有关农业相关问题
//
//                # 工作流程
//                1. 首先根据历史会话判断用户是否是首次提问
//                2. 如果是首次提问，引导用户描述问题
//                3. 如果不是首次提问，直接回答问题
//                4. 如果用户问题无法回答，说明无法回答
//                5. 如果用户问题涉及非农业领域，说明无法回答
//
//                # 回答规则
//                1. 提供专业农业知识
//                2. 使用实操性建议
//                3. 遇到非农业问题说明无法回答
//                4. 回答结构：问题分析→解决方案→补充建议
//                5. 使用通俗易懂的语言表达
//                6. 避免使用专业术语
//                7. 保持回答的简洁性和准确性
//
//                # 回答风格要求
//                   - 避免使用"很抱歉听到..."等客服套话
//                   - 采用"建议可以..."、"通常我们会..."等自然表达
//                   - 适当使用农业术语但要解释清楚
//                   - 涉及非农业领域无法回答时语气要委婉，主动引导用户描述问题
//
//                # 示例
//                问题：什么是农业？
//                回答：农业是指利用自然资源进行生产和生活的活动。农业可以分为种植、养殖、加工和销售等多个环节。
//
//                """
        );

        // 添加历史对话
//        for (OllamaChatMessage msg : context) {
//            sb.append(msg.getRole().getRoleName()).append(": ").append(msg.getContent()).append("\n");
//        }

        return sb.toString();
    }

}
