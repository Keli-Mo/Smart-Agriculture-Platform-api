package com.neu.system.task;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.neu.common.utils.DateUtils;
import com.neu.common.utils.StringUtils;
import com.neu.system.domain.AcwExhaustSchedule;
import com.neu.system.domain.AcwGreenhouse;
import com.neu.system.domain.AcwMessages;
import com.neu.system.service.IAcwExhaustScheduleService;
import com.neu.system.service.IAcwGreenhouseService;
import com.neu.system.service.IAcwMessagesService;
import com.neu.system.vo.ExhaustScheduleDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Component("AutoExhaustTask")
@Slf4j
public class AutoExhaustTask {
    @Autowired
    private IAcwExhaustScheduleService acwExhaustScheduleService;
    @Autowired
    private IAcwGreenhouseService acwGreenhouseService;
    @Autowired
    private IAcwMessagesService acwMessagesService;

    public void genAutoExhaustMsg() {
        // 获取当前时间
        Date currentTime = new Date();
        // 获取今天最新的排风计划
        String start = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, currentTime) + " 00:00:00";
        String end = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, currentTime) + " 23:59:59";
        acwGreenhouseService.selectAcwGreenhouseList(null).forEach(greenhouse -> {
            log.info("开始处理大棚：{}", greenhouse);
            //获取大棚今天最新的排风计划
            AcwExhaustSchedule filter = new AcwExhaustSchedule();
            filter.setGreenhouseId(greenhouse.getId());
            filter.getParams().put("beginCreateTime", start);
            filter.getParams().put("endCreateTime", end);
            PageHelper.startPage(1, 1);
            PageHelper.orderBy("create_time desc");
            List<AcwExhaustSchedule> schedules = acwExhaustScheduleService.selectAcwExhaustScheduleList(filter);
            if (schedules.isEmpty()) {
                log.warn("大棚{}今天没有排风计划", greenhouse.getName());
                return; // 如果没有排排计划，直接返回
            }
            AcwExhaustSchedule schedule = schedules.get(0);
            String json = schedule.getSchedule();
            if (StringUtils.isBlank(json)) {
                log.warn("大棚{}今天的排风计划为空", greenhouse.getName());
                return; // 如果没有排排计划，直接返回
            }
            // 解析排排计划
            ExhaustScheduleDetailVO scheduleDetailVO = JSON.parseObject(json, ExhaustScheduleDetailVO.class);
            if (scheduleDetailVO == null || scheduleDetailVO.getScheduleDetails() == null) {
                log.warn("大棚{}今天的排风计划解析失败", greenhouse.getName());
                return; // 如果没有排排计划，直接返回
            }
            // 获取当前时间
            LocalTime current = LocalTime.now();
            // 遍历排排计划
            for (ExhaustScheduleDetailVO.ScheduleDetail scheduleDetail : scheduleDetailVO.getScheduleDetails()) {
                log.info("排风计划：{}", scheduleDetail);
                LocalTime startTime = scheduleDetail.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
                LocalTime endTime = scheduleDetail.getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
                // 发送开启排风消息
                if (isTimeMatch(startTime, current)) {
                    if (hasSend(scheduleDetail.getStartTimeStr(), "1", String.valueOf(greenhouse.getId()))){
                        log.info("已经发送开启排风消息");
                        continue;
                    }
                    log.info("发送开启排风消息");
                    sendMsg("1", "greenhouse.exhaust.msg", scheduleDetail.getStartTimeStr(), greenhouse, schedule);
                }
                // 发送关闭排风消息
                if (isTimeMatch(endTime, current)) {
                    if (hasSend(scheduleDetail.getEndTimeStr(), "2", String.valueOf(greenhouse.getId()))){
                        log.info("已经发送关闭排风消息");
                        continue;
                    }
                    log.info("发送关闭排风消息");
                    sendMsg("2", "greenhouse.exhaust.msg", scheduleDetail.getEndTimeStr(), greenhouse, schedule);
                }
            }
        });
    }

    // 时间匹配检查（精确到分钟）
    private boolean isTimeMatch(LocalTime scheduleTime, LocalTime currentTime) {
        return scheduleTime.getHour() == currentTime.getHour() &&
                scheduleTime.getMinute() == currentTime.getMinute();
    }
    // 判断是否已经发送过消息
    private boolean hasSend(String scheduleTime, String type, String receiver) {
        // 获取当前时间
        Date currentTime = new Date();
        String start = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, currentTime) + " 00:00:00";
        String end = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, currentTime) + " 23:59:59";
        AcwMessages filter = new AcwMessages();
        filter.setCategory(type);
        filter.setReceiver(receiver);
        filter.getParams().put("beginSendTime", start);
        filter.getParams().put("beginReceiveTime", end);
        List<AcwMessages> messages = acwMessagesService.selectAcwMessagesList(filter);
        for (AcwMessages message : messages) {
            if (scheduleTime.equals(message.getMsgBody())){
                return true;
            }
        }
        return false;
    }

    private void sendMsg(String msgType, String topic, String body, AcwGreenhouse greenhouse, AcwExhaustSchedule schedule) {
        AcwMessages msg = new AcwMessages();
        msg.setCategory(msgType);//消息类别：1-开启排风 2-关闭排风
        msg.setMsgBody(body);
        msg.setContext(JSON.toJSONString(schedule));
        msg.setSender("自动排风检查");
        msg.setReceiver(String.valueOf(greenhouse.getId()));
        msg.setStatus("0");
        msg.setTopic(topic);
        msg.setSendTime(new Date());
        int i = acwMessagesService.insertAcwMessages(msg);
        if (i > 0) {
            log.info("发送开启或关闭消息成功");
        }
    }
}
