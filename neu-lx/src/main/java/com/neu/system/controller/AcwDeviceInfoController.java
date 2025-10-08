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
import com.neu.system.domain.AcwDeviceInfo;
import com.neu.system.service.IAcwDeviceInfoService;
import com.neu.common.utils.poi.ExcelUtil;
import com.neu.common.core.page.TableDataInfo;

/**
 * 设备管理Controller
 * 
 * @author lx
 * @date 2025-02-06
 */
@Tag(name = "设备管理", description = "设备管理")
@RestController
@RequestMapping("/system/deviceInfo")
public class AcwDeviceInfoController extends BaseController
{
    @Autowired
    private IAcwDeviceInfoService acwDeviceInfoService;

    /**
     * 查询设备管理列表
     */
    @Operation(summary = "查询设备管理列表")
    @Parameters({
            @Parameter(name = "serialNo", description = "设备编号"),
            @Parameter(name = "pageSize", description = "每页数据条数"),
            @Parameter(name = "pageNum", description = "页码"),
            @Parameter(name = "orderByColumn", description = "排序数据库表字段名"),
            @Parameter(name = "isAsc", description = "排序方式，asc升序，desc降序"),
    })
    @ApiResponse(useReturnTypeSchema = false, description = "返回设备管理列表", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "total", schema = @Schema(description = "数据总数", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "rows",
                                    array = @io.swagger.v3.oas.annotations.media.ArraySchema(schema = @Schema(description = "返回数据列表", implementation = AcwDeviceInfo.class)))
                    }
            )
    )
    @GetMapping("/list")
    public TableDataInfo list(@Schema(hidden = true) AcwDeviceInfo acwDeviceInfo)
    {
        startPage();
        List<AcwDeviceInfo> list = acwDeviceInfoService.selectAcwDeviceInfoList(acwDeviceInfo);
        return getDataTable(list);
    }

    /**
     * 导出设备管理列表
     */
    @Operation(summary = "导出设备管理列表")
    @Parameters({
            @Parameter(name = "serialNo", description = "设备编号"),
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
    @PreAuthorize("@ss.hasPermi('system:deviceInfo:export')")
    @Log(title = "设备管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(@Schema(hidden = true)AcwDeviceInfo acwDeviceInfo)
    {
        List<AcwDeviceInfo> list = acwDeviceInfoService.selectAcwDeviceInfoList(acwDeviceInfo);
        ExcelUtil<AcwDeviceInfo> util = new ExcelUtil<AcwDeviceInfo>(AcwDeviceInfo.class);
        return util.exportExcel(list, "设备管理数据");
    }

    /**
     * 获取设备管理详细信息
     */
    @Operation(summary = "获取设备管理详细信息")
    @Parameter(name = "id", description = "设备ID")
    @ApiResponse(useReturnTypeSchema = false, description = "返回结果JSON格式", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "data", schema = @Schema(description = "返回数据", implementation = AcwDeviceInfo.class))
                    }
            )
    )
    @PreAuthorize("@ss.hasPermi('system:deviceInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(acwDeviceInfoService.selectAcwDeviceInfoById(id));
    }

    /**
     * 新增设备管理
     */
    @Operation(summary = "新增设备管理")
    @Parameter(description = "设备实体", content = @io.swagger.v3.oas.annotations.media.Content(
            mediaType = "application/json",
            schema = @Schema(implementation = AcwDeviceInfo.class)))
    @ApiResponse(useReturnTypeSchema = false, description = "返回结果JSON格式", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "data", schema = @Schema(description = "返回数据", type = "Object"))
                    }
            )
    )
    @PreAuthorize("@ss.hasPermi('system:deviceInfo:add')")
    @Log(title = "设备管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AcwDeviceInfo acwDeviceInfo)
    {
        AcwDeviceInfo info = new AcwDeviceInfo();
        info.setSerialNo(acwDeviceInfo.getSerialNo());
        List<AcwDeviceInfo> list = acwDeviceInfoService.selectAcwDeviceInfoList(info);
        if(!list.isEmpty()){
            return AjaxResult.error("设备已存在，请输入新设备编号");
        }
        return toAjax(acwDeviceInfoService.insertAcwDeviceInfo(acwDeviceInfo));
    }

    /**
     * 修改设备管理
     */
    @Operation(summary = "修改设备管理")
    @Parameter(description = "设备实体", content = @io.swagger.v3.oas.annotations.media.Content(
            mediaType = "application/json",
            schema = @Schema(implementation = AcwDeviceInfo.class)))
    @ApiResponse(useReturnTypeSchema = false, description = "返回结果JSON格式", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "data", schema = @Schema(description = "返回数据", type = "Object"))
                    }
            )
    )
    @PreAuthorize("@ss.hasPermi('system:deviceInfo:edit')")
    @Log(title = "设备管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AcwDeviceInfo acwDeviceInfo)
    {
        return toAjax(acwDeviceInfoService.updateAcwDeviceInfo(acwDeviceInfo));
    }

    /**
     * 删除设备管理
     */
    @Operation(summary = "删除设备管理")
    @Parameter(name = "ids", description = "设备ID")
    @ApiResponse(useReturnTypeSchema = false, description = "返回结果JSON格式", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "data", schema = @Schema(description = "返回数据", type = "Object"))
                    }
            )
    )
    @PreAuthorize("@ss.hasPermi('system:deviceInfo:remove')")
    @Log(title = "设备管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(acwDeviceInfoService.deleteAcwDeviceInfoByIds(ids));
    }
}
