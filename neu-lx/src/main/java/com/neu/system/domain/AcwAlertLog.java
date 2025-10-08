package com.neu.system.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.neu.common.annotation.Excel;
import com.neu.common.core.domain.BaseEntity;

/**
 * 预警记录对象 acw_alert_log
 *
 * @author neu
 * @date 2025-03-25
 */
@Schema(description = "预警记录对象",title = "com.neu.system.domain.AcwAlertLog")
public class AcwAlertLog extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 水域
     */
    @Excel(name = "大棚id")
    private Long ghId;

    /**
     * 设备编码
     */
    @Excel(name = "设备编码")
    private String deviceNo;

    /**
     * 温度
     */
    @Excel(name = "温度")
    private String temperature;

    /**
     * 湿度
     */
    @Excel(name = "湿度")
    private String humility;

    /**
     * 烟雾
     */
    @Excel(name = "烟雾")
    private String smoke;

    /**
     * pm2.5
     */
    @Excel(name = "PM2.5")
    private String pm25;

    /** 预警信息 */
    @Excel(name = "预警信息")
    private String alert;

    /**
     * 预警类型
     */
    @Excel(name = "预警类型")
    private String type;

    private AcwGreenhouse greenhouse;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setGhId(Long ghId) {
        this.ghId = ghId;
    }

    public Long getGhId() {
        return ghId;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setHumility(String humility) {
        this.humility = humility;
    }

    public String getHumility() {
        return humility;
    }

    public void setSmoke(String smoke) {
        this.smoke = smoke;
    }

    public String getSmoke() {
        return smoke;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getPm25() {
        return pm25;
    }

    public void setAlert(String alert)
    {
        this.alert = alert;
    }

    public String getAlert()
    {
        return alert;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public AcwGreenhouse getGreenhouse() {
        return greenhouse;
    }

    public void setGreenhouse(AcwGreenhouse greenhouse) {
        this.greenhouse = greenhouse;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("poolId", getGhId())
                .append("deviceNo", getDeviceNo())
                .append("temperature", getTemperature())
                .append("quality", getHumility())
                .append("ph", getSmoke())
                .append("turbidity", getPm25())
                .append("type", getType())
                .append("createTime", getCreateTime())
                .append("createBy", getCreateBy())
                .toString();
    }


}
