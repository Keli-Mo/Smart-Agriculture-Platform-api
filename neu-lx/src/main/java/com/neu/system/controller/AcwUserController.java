package com.neu.system.controller;

import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.neu.common.annotation.Log;
import com.neu.common.core.controller.BaseController;
import com.neu.common.core.domain.AjaxResult;
import com.neu.common.enums.BusinessType;
import com.neu.system.domain.AcwUser;
import com.neu.system.service.IAcwUserService;
import com.neu.common.utils.poi.ExcelUtil;
import com.neu.common.core.page.TableDataInfo;

/**
 * 用户信息Controller
 *
 * @author lx
 * @date 2025-02-11
 */
@Tag(name = "用户信息", description = "用户信息")
@RestController
@RequestMapping("/system/acwUser")
public class AcwUserController extends BaseController {
    @Autowired
    private IAcwUserService acwUserService;

    /**
     * 查询用户信息列表
     */
    @Operation(summary = "查询用户信息列表")
    @Parameters({
            @Parameter(name = "pageSize", description = "每页数据条数"),
            @Parameter(name = "pageNum", description = "页码"),
            @Parameter(name = "orderByColumn", description = "排序数据库表字段名"),
            @Parameter(name = "isAsc", description = "排序方式，asc升序，desc降序"),
            @Parameter(name = "sysUserId", description = "系统用户ID"),
            @Parameter(name = "realName", description = "真实姓名"),
            @Parameter(name = "cardNo", description = "身份证号"),
    })
    @ApiResponse(useReturnTypeSchema = false, description = "返回用户信息列表", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "total", schema = @Schema(description = "数据总数", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "rows",
                                    array = @io.swagger.v3.oas.annotations.media.ArraySchema(schema = @Schema(description = "返回数据列表", implementation = AcwUser.class)))
                    }
            )
    )
    @GetMapping("/list")
    public TableDataInfo list(@Schema(hidden = true)AcwUser acwUser) {
        startPage();
        List<AcwUser> list = acwUserService.selectAcwUserList(acwUser);
        return getDataTable(list);
    }

    /**
     * 导出用户信息列表
     */
    @Operation(summary = "导出用户信息列表")
    @Parameters({
            @Parameter(name = "sysUserId", description = "系统用户ID"),
            @Parameter(name = "realName", description = "真实姓名"),
            @Parameter(name = "cardNo", description = "身份证号"),
    })
    @ApiResponse(useReturnTypeSchema = false, description = "返回结果JSON格式", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "data", schema = @Schema(description = "生成的文件名", type = "String"))
                    }
            )
    )
    @Log(title = "用户信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(@Schema(hidden = true)AcwUser acwUser) {
        List<AcwUser> list = acwUserService.selectAcwUserList(acwUser);
        ExcelUtil<AcwUser> util = new ExcelUtil<AcwUser>(AcwUser.class);
        return util.exportExcel(list, "用户信息数据");
    }

    /**
     * 获取用户信息详细信息
     */
    @Operation(summary = "获取用户信息详细信息")
    @Parameter(name = "id", description = "用户ID")
    @ApiResponse(useReturnTypeSchema = false, description = "返回结果JSON格式", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "data", schema = @Schema(description = "返回数据", implementation = AcwUser.class))
                    }
            )
    )
    @PreAuthorize("@ss.hasPermi('system:acwUser:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(acwUserService.selectAcwUserById(id));
    }

    /**
     * 新增用户信息
     */
    @Operation(summary = "新增用户信息")
    @Parameter(description = "用户实体", content = @io.swagger.v3.oas.annotations.media.Content(
            mediaType = "application/json",
            schema = @Schema(implementation = AcwUser.class)))
    @ApiResponse(useReturnTypeSchema = false, description = "返回结果JSON格式", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "data", schema = @Schema(description = "返回数据", type = "Object"))
                    }
            )
    )
    @Log(title = "用户信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AcwUser acwUser) {
        AcwUser filter = new AcwUser();
        filter.setSysUserId(acwUser.getSysUserId());
        List<AcwUser> list = acwUserService.selectAcwUserList(filter);
        if (list != null && !list.isEmpty()) {
            acwUser.setId(list.get(0).getId());
            return toAjax(acwUserService.updateAcwUser(acwUser));
        } else {
            return toAjax(acwUserService.insertAcwUser(acwUser));
        }
    }

    /**
     * 修改用户信息
     */
    @Operation(summary = "修改用户信息")
    @Parameter(description = "用户实体", content = @io.swagger.v3.oas.annotations.media.Content(
            mediaType = "application/json",
            schema = @Schema(implementation = AcwUser.class)))
    @ApiResponse(useReturnTypeSchema = false, description = "返回结果JSON格式", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "data", schema = @Schema(description = "返回数据", type = "Object"))
                    }
            )
    )
    @Log(title = "用户信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AcwUser acwUser) {
        return toAjax(acwUserService.updateAcwUser(acwUser));
    }

    /**
     * 删除用户信息
     */
    @Operation(summary = "删除用户信息")
    @Parameter(name = "ids", description = "用户ID数组")
    @ApiResponse(useReturnTypeSchema = false, description = "返回结果JSON格式", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "data", schema = @Schema(description = "返回数据", type = "Object"))
                    }
            )
    )
    @PreAuthorize("@ss.hasPermi('system:acwUser:remove')")
    @Log(title = "用户信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(acwUserService.deleteAcwUserByIds(ids));
    }
}
