package com.neu.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.neu.common.annotation.Excel;
import com.neu.common.core.domain.BaseEntity;

/**
 * 消息对象 acw_messages
 * 
 * @author neu
 * @date 2025-05-13
 */
@Schema(description = "消息对象",title = "com.neu.system.domain.AcwMessages")
public class AcwMessages extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 消息主题 */
    @Excel(name = "消息主题")
    private String topic;

    /** 消息类型 */
    @Excel(name = "消息类型")
    private String category;

    /** 消息体 */
    private String msgBody;

    /** 消息上下文 */
    private String context;

    /** 发送时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发送时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date sendTime;

    /** 接收时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "接收时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date receiveTime;

    /** 发送者 */
    @Excel(name = "发送者")
    private String sender;

    /** 接收者 */
    @Excel(name = "接收者")
    private String receiver;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTopic(String topic) 
    {
        this.topic = topic;
    }

    public String getTopic() 
    {
        return topic;
    }
    public void setCategory(String category) 
    {
        this.category = category;
    }

    public String getCategory() 
    {
        return category;
    }
    public void setMsgBody(String msgBody) 
    {
        this.msgBody = msgBody;
    }

    public String getMsgBody() 
    {
        return msgBody;
    }
    public void setContext(String context) 
    {
        this.context = context;
    }

    public String getContext() 
    {
        return context;
    }
    public void setSendTime(Date sendTime) 
    {
        this.sendTime = sendTime;
    }

    public Date getSendTime() 
    {
        return sendTime;
    }
    public void setReceiveTime(Date receiveTime) 
    {
        this.receiveTime = receiveTime;
    }

    public Date getReceiveTime() 
    {
        return receiveTime;
    }
    public void setSender(String sender) 
    {
        this.sender = sender;
    }

    public String getSender() 
    {
        return sender;
    }
    public void setReceiver(String receiver) 
    {
        this.receiver = receiver;
    }

    public String getReceiver() 
    {
        return receiver;
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
            .append("topic", getTopic())
            .append("category", getCategory())
            .append("msgBody", getMsgBody())
            .append("context", getContext())
            .append("sendTime", getSendTime())
            .append("receiveTime", getReceiveTime())
            .append("sender", getSender())
            .append("receiver", getReceiver())
            .append("status", getStatus())
            .toString();
    }
}
