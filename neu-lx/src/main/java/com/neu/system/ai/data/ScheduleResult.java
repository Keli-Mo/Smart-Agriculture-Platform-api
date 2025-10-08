package com.neu.system.ai.data;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


/**
 * 排风计划
 */
@Data
public class ScheduleResult {
    private String reason;
    private List<Schedule> schedules = new ArrayList<>();

    @Data
    public static class Schedule {
        private String startTime;
        private int duration;
    }

    public void toObject(JSONObject jsonObject) {
        reason = jsonObject.getString("reason");
        JSONArray jsonArray = jsonObject.getJSONArray("schedules");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject sch = jsonArray.getJSONObject(i);
            Schedule schedule = new Schedule();
            schedule.setStartTime(sch.getString("startTime"));
            schedule.setDuration(sch.getInteger("duration"));
            schedules.add(schedule);
        }
    }
}
