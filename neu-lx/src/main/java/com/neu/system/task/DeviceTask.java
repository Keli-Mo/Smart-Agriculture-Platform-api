package com.neu.system.task;

import com.neu.common.utils.DateUtils;
import com.neu.system.domain.AcwDeviceInfo;
import com.neu.system.service.IAcwDeviceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 设备相关的自动化任务
 */
@Component("DeviceTask")
public class DeviceTask {

    //设备离线超时阈值毫秒数
    private static final long TIMEOUT_THRESHHOLD = 30 * 1000;


    @Autowired
    private IAcwDeviceInfoService acwDeviceInfoService;

    /**
     * 检查设备状态，自动离线
     */
    public void checkDeviceStatus() {
        //获取注册的设备
        List<AcwDeviceInfo> deviceList = acwDeviceInfoService.selectAcwDeviceInfoList(null);
        deviceList.forEach(device -> {
            String deviceNo = device.getSerialNo();
            //查询当前日期24小时内的生命体征数据
            Date date = new Date();
            String start = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, date);
            String end = start + " 23:59:59";

        });
    }
}
