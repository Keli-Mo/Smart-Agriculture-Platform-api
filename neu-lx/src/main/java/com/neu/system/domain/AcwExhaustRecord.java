package com.neu.system.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.neu.common.annotation.Excel;
import com.neu.common.core.domain.BaseEntity;

/**
 * 排风记录对象 acw_exhaust_record
 * 
 * @author neu
 * @date 2025-05-14
 */
@Schema(description = "排风记录对象", title = "com.neu.system.domain.AcwExhaustRecord")
public class AcwExhaustRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 大棚ID */
    @Excel(name = "大棚ID")
    private Long greenhouseId;

    /** 计划ID */
    @Excel(name = "计划ID")
    private Long scheduleId;

    /** 执行动作 */
    @Excel(name = "执行动作")
    private String action;

    /** 执行时长 */
    @Excel(name = "执行时长")
    private String duration;

    private AcwGreenhouse greenhouse;

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
    public void setScheduleId(Long scheduleId) 
    {
        this.scheduleId = scheduleId;
    }

    public Long getScheduleId() 
    {
        return scheduleId;
    }
    public void setAction(String action) 
    {
        this.action = action;
    }

    public String getAction() 
    {
        return action;
    }
    public void setDuration(String duration) 
    {
        this.duration = duration;
    }

    public String getDuration() 
    {
        return duration;
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
            .append("greenhouseId", getGreenhouseId())
            .append("scheduleId", getScheduleId())
            .append("action", getAction())
            .append("duration", getDuration())
            .append("createTime", getCreateTime())
            .toString();
    }


}
