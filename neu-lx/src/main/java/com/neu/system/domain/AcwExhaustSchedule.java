package com.neu.system.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.neu.common.annotation.Excel;
import com.neu.common.core.domain.BaseEntity;

/**
 * 排风计划对象 acw_exhaust_schedule
 * 
 * @author neu
 * @date 2025-05-14
 */
@Schema(description = "排风计划对象", title = "com.neu.system.domain.AcwExhaustSchedule")
public class AcwExhaustSchedule extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 大棚ID */
    @Excel(name = "大棚ID")
    private Long greenhouseId;

    /** 计划详情 */
    private String schedule;

    /** 分析 */
    @Excel(name = "分析")
    private String reason;

    /** 天气 */
    @Excel(name = "天气")
    private String weather;

    /** 是否生效 */
    @Excel(name = "是否生效")
    private String status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setGreenhouseId(Long greenhouseId) 
    {
        this.greenhouseId = greenhouseId;
    }

    public Long getGreenhouseId() 
    {
        return greenhouseId;
    }
    public void setSchedule(String schedule) 
    {
        this.schedule = schedule;
    }

    public String getSchedule() 
    {
        return schedule;
    }
    public void setReason(String reason) 
    {
        this.reason = reason;
    }

    public String getReason() 
    {
        return reason;
    }
    public void setWeather(String weather) 
    {
        this.weather = weather;
    }

    public String getWeather() 
    {
        return weather;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("greenhouseId", getGreenhouseId())
            .append("schedule", getSchedule())
            .append("reason", getReason())
            .append("weather", getWeather())
            .append("createTime", getCreateTime())
            .append("status", getStatus())
            .toString();
    }
}
