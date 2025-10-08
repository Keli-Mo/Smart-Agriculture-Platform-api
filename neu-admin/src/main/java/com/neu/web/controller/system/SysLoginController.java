package com.neu.web.controller.system;

import com.neu.common.constant.Constants;
import com.neu.common.core.domain.AjaxResult;
import com.neu.common.core.domain.entity.SysMenu;
import com.neu.common.core.domain.entity.SysUser;
import com.neu.common.core.domain.model.LoginBody;
import com.neu.common.core.domain.model.LoginBodyWithoutCode;
import com.neu.common.core.domain.model.LoginUser;
import com.neu.common.utils.ServletUtils;
import com.neu.framework.web.service.SysLoginService;
import com.neu.framework.web.service.SysPermissionService;
import com.neu.framework.web.service.TokenService;
import com.neu.system.domain.AcwDeviceInfo;
import com.neu.system.service.ISysMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * 登录验证
 * 
 * @author neu
 */
@RestController
@Tag(name = "App登录")
public class SysLoginController
{
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private TokenService tokenService;

    /**
     * 登录方法
     * 
     * @param loginBody 登录信息
     * @return 结果
     */
    @Operation(summary = "验证码登录")
    @Parameter(description = "登录用户信息", content = @io.swagger.v3.oas.annotations.media.Content(
            mediaType = "application/json",
            schema = @Schema(implementation = LoginBody.class)))
    @ApiResponse(useReturnTypeSchema = false, description = "返回结果JSON格式", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "token", schema = @Schema(description = "登录成功返回的token", type = "String"))
                    }
            )
    )
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody)
    {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * 不使用验证码登录
     *
     * @param loginBodyWithoutCode 登录信息
     * @return 结果
     */
    @Operation(summary = "不使用验证码登录")
    @Parameter(description = "登录用户信息", content = @io.swagger.v3.oas.annotations.media.Content(
            mediaType = "application/json",
            schema = @Schema(implementation = LoginBodyWithoutCode.class)))
    @ApiResponse(useReturnTypeSchema = false, description = "返回结果JSON格式", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "token", schema = @Schema(description = "登录成功返回的token", type = "String"))
                    }
            )
    )
    @PostMapping("/loginWithoutCode")
    public AjaxResult loginWithoutCode(@RequestBody LoginBodyWithoutCode loginBodyWithoutCode)
    {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.loginWithoutCode(loginBodyWithoutCode.getUsername(), loginBodyWithoutCode.getPassword());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * 获取用户信息
     * 
     * @return 用户信息
     */
    @GetMapping("getInfo")
    @Operation(summary = "获取登录对象")
    @ApiResponse(useReturnTypeSchema = false, description = "返回结果JSON格式", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "user", schema = @Schema(description = "用户信息", type = "SysUser")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "roles", schema = @Schema(description = "角色集合", type = "Set<String>")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "permissions", schema = @Schema(description = "权限集合", type = "Set<String>"))
                    }
            )
    )
    public AjaxResult getInfo()
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        if(loginUser == null){
            return AjaxResult.error("请重新登录");
        }
        SysUser user = loginUser.getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);

        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);

        return ajax;
    }

    /**
     * 获取路由信息
     * 
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters()
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        // 用户信息
        SysUser user = loginUser.getUser();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(user.getUserId());
        return AjaxResult.success(menuService.buildMenus(menus));
    }
}
