package com.neu.system.vo;

import com.neu.common.utils.DateUtils;
import com.neu.system.ai.data.ScheduleResult;
import com.neu.system.domain.AcwGreenhouse;
import lombok.Data;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ExhaustScheduleDetailVO {
    private Long greenhouseId;
    private List<ScheduleDetail> scheduleDetails = new ArrayList<>();

    @Data
    public static class ScheduleDetail {
        private Date startTime;
        private int duration;
        private Date endTime;
        private String startTimeStr;
        private String endTimeStr;
    }

    public void toObject(List<ScheduleResult.Schedule> schedules, AcwGreenhouse greenhouse) {
        setGreenhouseId(greenhouse.getId());
        schedules.forEach(schedule -> {
            try {
                // 根据开始时间和排风时长计算开启时间和结束时间
                String startTime = schedule.getStartTime();
                int duration = schedule.getDuration();
                String today = DateUtils.getDate();
                String fullDate = today + " " + startTime;
                Date startDate = DateUtils.parseDate(fullDate, "yyyy-MM-dd HH:mm");
                Date endDate = DateUtils.addMinutes(startDate, duration);
                ScheduleDetail scheduleDetail = new ScheduleDetail();
                scheduleDetail.setStartTime(startDate);
                scheduleDetail.setDuration(duration);
                scheduleDetail.setEndTime(endDate);
                scheduleDetail.setStartTimeStr(fullDate);
                scheduleDetail.setEndTimeStr(DateUtils.parseDateToStr("yyyy-MM-dd HH:mm", endDate));
                scheduleDetails.add(scheduleDetail);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
