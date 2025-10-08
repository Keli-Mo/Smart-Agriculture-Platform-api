package com.neu.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.neu.common.annotation.Excel;
import com.neu.common.core.domain.BaseEntity;

/**
 * 大棚监测对象 acw_greenhouse_monitor
 * 
 * @author neu
 * @date 2025-03-26
 */
@Schema(description = "大棚监测对象",title = "com.neu.system.domain.AcwGreenhouseMonitor")
public class AcwGreenhouseMonitor extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 大棚 */
    @Excel(name = "大棚")
    private Long ghId;

    /** 设备编号 */
    @Excel(name = "设备编号")
    private String deviceNo;

    /** 温度 */
    @Excel(name = "温度")
    private String temperature;

    /** 湿度 */
    @Excel(name = "湿度")
    private String humility;

    /** 烟雾 */
    @Excel(name = "烟雾")
    private String smoke;

    /** PM2.5 */
    @Excel(name = "PM2.5")
    private String pm25;

    /** 监测时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "监测时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date dataTime;

    private AcwGreenhouse greenhouse;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setGhId(Long ghId) 
    {
        this.ghId = ghId;
    }

    public Long getGhId() 
    {
        return ghId;
    }
    public void setDeviceNo(String deviceNo) 
    {
        this.deviceNo = deviceNo;
    }

    public String getDeviceNo() 
    {
        return deviceNo;
    }
    public void setTemperature(String temperature) 
    {
        this.temperature = temperature;
    }

    public String getTemperature() 
    {
        return temperature;
    }
    public void setHumility(String humility) 
    {
        this.humility = humility;
    }

    public String getHumility() 
    {
        return humility;
    }
    public void setSmoke(String smoke) 
    {
        this.smoke = smoke;
    }

    public String getSmoke() 
    {
        return smoke;
    }
    public void setPm25(String pm25) 
    {
        this.pm25 = pm25;
    }

    public String getPm25() 
    {
        return pm25;
    }
    public void setDataTime(Date dataTime) 
    {
        this.dataTime = dataTime;
    }

    public Date getDataTime() 
    {
        return dataTime;
    }


    public AcwGreenhouse getGreenhouse() {
        return greenhouse;
    }

    public void setGreenhouse(AcwGreenhouse greenhouse) {
        this.greenhouse = greenhouse;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("ghId", getGhId())
            .append("deviceNo", getDeviceNo())
            .append("temperature", getTemperature())
            .append("humility", getHumility())
            .append("smoke", getSmoke())
            .append("pm25", getPm25())
            .append("dataTime", getDataTime())
            .toString();
    }

}
