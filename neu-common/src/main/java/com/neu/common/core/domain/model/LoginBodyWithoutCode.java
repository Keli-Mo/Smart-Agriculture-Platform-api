package com.neu.common.core.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 用户登录对象
 * 
 * @author neu
 */

@Schema(description = "无需验证码登录时的登录对象")
public class LoginBodyWithoutCode
{
    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String username;

    /**
     * 用户密码
     */
    @Schema(description = "用户密码")
    private String password;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
