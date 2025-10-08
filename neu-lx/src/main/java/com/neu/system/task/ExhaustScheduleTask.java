package com.neu.system.task;

import com.alibaba.fastjson.JSON;
import com.neu.common.utils.StringUtils;
import com.neu.system.ai.AgricultureAIService;
import com.neu.system.ai.WeatherService;
import com.neu.system.ai.data.RegeoData;
import com.neu.system.ai.data.ScheduleResult;
import com.neu.system.ai.data.WeatherData;
import com.neu.system.domain.AcwExhaustSchedule;
import com.neu.system.domain.AcwGreenhouse;
import com.neu.system.service.IAcwExhaustScheduleService;
import com.neu.system.service.IAcwGreenhouseService;
import com.neu.system.vo.ExhaustScheduleDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("ExhaustScheduleTask")
@Slf4j
public class ExhaustScheduleTask {
    public static final String DEFAULT_CODE = "110000";
    @Autowired
    private IAcwGreenhouseService acwGreenhouseService;
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private AgricultureAIService agricultureAIService;
    @Autowired
    private IAcwExhaustScheduleService acwExhaustScheduleService;

    public void genSchedule() {
        // 获取天气信息
        try {
            log.info("开始生成排风计划");
            // 获取大棚信息
            AcwGreenhouse filter = new AcwGreenhouse();
            acwGreenhouseService.selectAcwGreenhouseList(filter).forEach(greenhouse -> {
                log.info("开始处理大棚：{}", greenhouse);
                String corps = greenhouse.getCrop();
                String lng = greenhouse.getLng();
                String lat = greenhouse.getLat();
                WeatherData.LiveWeather liveWeather = null;
                try {
                    String adcode = DEFAULT_CODE;
                    if (StringUtils.isBlank(lng) || StringUtils.isBlank(lat)) {
                        log.warn("大棚{}的经纬度为空，使用默认值", greenhouse.getName());
                    }
                    RegeoData regeoData = weatherService.getRegeoData(Double.parseDouble(lng), Double.parseDouble(lat));
                    if (regeoData != null && regeoData.getStatus().equals("1")) {
                        RegeoData.Regeocode regeocode = regeoData.getRegeocode();
                        if (regeocode != null) {
                            RegeoData.AddressComponent addressComponent = regeocode.getAddressComponent();
                            if (addressComponent != null) {
                                String province = addressComponent.getProvince();
//                                String city = addressComponent.getCity();
//                                String district = addressComponent.getDistrict();
                                adcode = addressComponent.getAdcode();
                                log.info("获取到的地址信息：province={}, adcode={}", province, adcode);
                            }
                        }
                    }
                    liveWeather = weatherService.getLiveWeather(adcode);
                } catch (Exception e) {
                    log.error("获取天气信息失败", e);
                }
                log.info("获取天气信息：{}", liveWeather);
                ScheduleResult result = agricultureAIService.getExhauseSchedule(liveWeather, corps);
                log.info("获取排风计划：{}", result);
                if (result != null) {
                    List<ScheduleResult.Schedule> schedules = result.getSchedules();
                    // 保存排风计划
                    if (schedules != null && !schedules.isEmpty()) {
                        saveSchedule(schedules, greenhouse, liveWeather, result.getReason());
                    }
                }
            });
        } catch (Exception e) {
            log.error("生成排风计划失败", e);
        }
    }

    private void saveSchedule(List<ScheduleResult.Schedule> schedules, AcwGreenhouse greenhouse, WeatherData.LiveWeather liveWeather, String reason) {
        ExhaustScheduleDetailVO detailVO = new ExhaustScheduleDetailVO();
        detailVO.toObject(schedules, greenhouse);
        // 保存排风计划
        AcwExhaustSchedule acwExhaustSchedule = new AcwExhaustSchedule();
        acwExhaustSchedule.setGreenhouseId(greenhouse.getId());
        acwExhaustSchedule.setSchedule(JSON.toJSONString(detailVO));
        acwExhaustSchedule.setStatus("1");
        acwExhaustSchedule.setWeather(liveWeather != null ? liveWeather.formatWeather() : "");
        acwExhaustSchedule.setReason(reason);
        int i = acwExhaustScheduleService.insertAcwExhaustSchedule(acwExhaustSchedule);
        if (i > 0) {
            log.info("保存排风计划成功：{}", acwExhaustSchedule);
        }
    }
}
