package com.neu.system.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.neu.common.annotation.Excel;
import com.neu.common.core.domain.BaseEntity;

/**
 * 大棚信息对象 acw_greenhouse
 * 
 * @author neu
 * @date 2025-03-26
 */
@Schema(description = "大棚信息对象",title = "com.neu.system.domain.AcwGreenhouse")
public class AcwGreenhouse extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 详细位置 */
    @Excel(name = "详细位置")
    private String location;

    /** 长 */
    @Excel(name = "长")
    private String length;

    /** 宽 */
    @Excel(name = "宽")
    private String width;

    /** 高 */
    @Excel(name = "高")
    private String height;

    /** 种植面积 */
    @Excel(name = "种植面积")
    private String area;

    /** 作物 */
    @Excel(name = "作物")
    private String crop;

    /** 经度 */
    @Excel(name = "经度")
    private String lng;

    /** 纬度 */
    @Excel(name = "纬度")
    private String lat;

    /** 图片 */
    private String picture;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setLocation(String location) 
    {
        this.location = location;
    }

    public String getLocation() 
    {
        return location;
    }
    public void setLength(String length) 
    {
        this.length = length;
    }

    public String getLength() 
    {
        return length;
    }
    public void setWidth(String width) 
    {
        this.width = width;
    }

    public String getWidth() 
    {
        return width;
    }
    public void setHeight(String height) 
    {
        this.height = height;
    }

    public String getHeight() 
    {
        return height;
    }
    public void setArea(String area) 
    {
        this.area = area;
    }

    public String getArea() 
    {
        return area;
    }
    public void setCrop(String crop) 
    {
        this.crop = crop;
    }

    public String getCrop() 
    {
        return crop;
    }
    public void setLng(String lng) 
    {
        this.lng = lng;
    }

    public String getLng() 
    {
        return lng;
    }
    public void setLat(String lat) 
    {
        this.lat = lat;
    }

    public String getLat() 
    {
        return lat;
    }
    public void setPicture(String picture) 
    {
        this.picture = picture;
    }

    public String getPicture() 
    {
        return picture;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("location", getLocation())
            .append("length", getLength())
            .append("width", getWidth())
            .append("height", getHeight())
            .append("area", getArea())
            .append("crop", getCrop())
            .append("lng", getLng())
            .append("lat", getLat())
            .append("picture", getPicture())
            .append("remark", getRemark())
            .toString();
    }
}
