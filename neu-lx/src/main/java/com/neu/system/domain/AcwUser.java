package com.neu.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.neu.common.annotation.Excel;
import com.neu.common.core.domain.BaseEntity;

/**
 * 用户信息对象 acw_user
 *
 * @author lx
 * @date 2025-02-11
 */
@Schema(description = "用户信息对象",title = "com.neu.system.domain.AcwUser")
public class AcwUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 系统用户ID */
    private Long sysUserId;

    /** 真实姓名 */
    @Excel(name = "真实姓名")
    private String realName;

    /** 性别 */
    @Excel(name = "性别")
    private Long sex;

    /** 年龄 */
    @Excel(name = "年龄")
    private Long age;

    /** 身份证号 */
    @Excel(name = "身份证号")
    private String cardNo;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String phone;

    /** 居住地址 */
    @Excel(name = "居住地址")
    private String residentialAddress;

    /** 户籍地址 */
    @Excel(name = "户籍地址")
    private String permanentAddress;

    /** 生日 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date birthday;

    /** 籍贯 */
    @Excel(name = "籍贯")
    private String nativePlace;

    /** 民族 */
    @Excel(name = "民族")
    private Long nation;

    /** 状态 */
    @Excel(name = "状态")
    private Long state;

    /** 头像 */
    @Excel(name = "头像")
    private String photoUrl;

    /** 扩展1 */
    private String extendCol1;

    /** 扩展2 */
    private String extendCol2;

    /** 扩展3 */
    private String extendCol3;

    /** 扩展4 */
    private String extendCol4;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setSysUserId(Long sysUserId)
    {
        this.sysUserId = sysUserId;
    }

    public Long getSysUserId()
    {
        return sysUserId;
    }
    public void setRealName(String realName)
    {
        this.realName = realName;
    }

    public String getRealName()
    {
        return realName;
    }
    public void setSex(Long sex)
    {
        this.sex = sex;
    }

    public Long getSex()
    {
        return sex;
    }
    public void setAge(Long age)
    {
        this.age = age;
    }

    public Long getAge()
    {
        return age;
    }
    public void setCardNo(String cardNo)
    {
        this.cardNo = cardNo;
    }

    public String getCardNo()
    {
        return cardNo;
    }
    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getPhone()
    {
        return phone;
    }
    public void setResidentialAddress(String residentialAddress)
    {
        this.residentialAddress = residentialAddress;
    }

    public String getResidentialAddress()
    {
        return residentialAddress;
    }
    public void setPermanentAddress(String permanentAddress)
    {
        this.permanentAddress = permanentAddress;
    }

    public String getPermanentAddress()
    {
        return permanentAddress;
    }
    public void setBirthday(Date birthday)
    {
        this.birthday = birthday;
    }

    public Date getBirthday()
    {
        return birthday;
    }
    public void setNativePlace(String nativePlace)
    {
        this.nativePlace = nativePlace;
    }

    public String getNativePlace()
    {
        return nativePlace;
    }
    public void setNation(Long nation)
    {
        this.nation = nation;
    }

    public Long getNation()
    {
        return nation;
    }
    public void setState(Long state)
    {
        this.state = state;
    }

    public Long getState()
    {
        return state;
    }
    public void setPhotoUrl(String photoUrl)
    {
        this.photoUrl = photoUrl;
    }

    public String getPhotoUrl()
    {
        return photoUrl;
    }
    public void setExtendCol1(String extendCol1)
    {
        this.extendCol1 = extendCol1;
    }

    public String getExtendCol1()
    {
        return extendCol1;
    }
    public void setExtendCol2(String extendCol2)
    {
        this.extendCol2 = extendCol2;
    }

    public String getExtendCol2()
    {
        return extendCol2;
    }
    public void setExtendCol3(String extendCol3)
    {
        this.extendCol3 = extendCol3;
    }

    public String getExtendCol3()
    {
        return extendCol3;
    }
    public void setExtendCol4(String extendCol4)
    {
        this.extendCol4 = extendCol4;
    }

    public String getExtendCol4()
    {
        return extendCol4;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("sysUserId", getSysUserId())
                .append("realName", getRealName())
                .append("sex", getSex())
                .append("age", getAge())
                .append("cardNo", getCardNo())
                .append("phone", getPhone())
                .append("residentialAddress", getResidentialAddress())
                .append("permanentAddress", getPermanentAddress())
                .append("birthday", getBirthday())
                .append("nativePlace", getNativePlace())
                .append("nation", getNation())
                .append("state", getState())
                .append("photoUrl", getPhotoUrl())
                .append("createTime", getCreateTime())
                .append("createBy", getCreateBy())
                .append("updateTime", getUpdateTime())
                .append("updateBy", getUpdateBy())
                .append("extendCol1", getExtendCol1())
                .append("extendCol2", getExtendCol2())
                .append("extendCol3", getExtendCol3())
                .append("extendCol4", getExtendCol4())
                .toString();
    }
}