package com.neu.system.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.neu.common.annotation.Excel;
import com.neu.common.core.domain.BaseEntity;

/**
 * 聊天记录对象 acw_chat_messages
 * 
 * @author neu
 * @date 2025-05-14
 */
@Schema(description = "聊天记录对象",title = "com.neu.system.domain.AcwChatMessages")
public class AcwChatMessages extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 角色(user/assistant) */
    @Excel(name = "角色(user/assistant)")
    private String role;

    /** 消息内容 */
    @Excel(name = "消息内容")
    private String content;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setRole(String role) 
    {
        this.role = role;
    }

    public String getRole() 
    {
        return role;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("role", getRole())
            .append("content", getContent())
            .append("createTime", getCreateTime())
            .toString();
    }
}
