package com.neu.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.neu.common.annotation.Excel;
import com.neu.common.core.domain.BaseEntity;

/**
 * 设备管理对象 acw_device_info
 * 
 * @author lx
 * @date 2025-02-06
 */
@Schema(description = "设备管理对象", title = "com.neu.system.domain.AcwDeviceInfo")
public class AcwDeviceInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 设备编号 */
    @Excel(name = "设备编号")
    private String serialNo;

    /** 设备类型 */
    @Excel(name = "设备类型")
    private String deviceType;

    /** 设备状态 */
    @Excel(name = "设备状态")
    private String status;

    /** 注册日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "注册日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date registerDate;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setSerialNo(String serialNo) 
    {
        this.serialNo = serialNo;
    }

    public String getSerialNo() 
    {
        return serialNo;
    }
    public void setDeviceType(String deviceType) 
    {
        this.deviceType = deviceType;
    }

    public String getDeviceType() 
    {
        return deviceType;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setRegisterDate(Date registerDate) 
    {
        this.registerDate = registerDate;
    }

    public Date getRegisterDate() 
    {
        return registerDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("serialNo", getSerialNo())
            .append("deviceType", getDeviceType())
            .append("status", getStatus())
            .append("registerDate", getRegisterDate())
            .toString();
    }
}
