package com.neu.system.controller;

import java.util.List;

import com.neu.system.domain.AcwExhaustRecord;
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
import com.neu.system.domain.AcwExhaustSchedule;
import com.neu.system.service.IAcwExhaustScheduleService;
import com.neu.common.utils.poi.ExcelUtil;
import com.neu.common.core.page.TableDataInfo;

/**
 * 排风计划Controller
 * 
 * @author neu
 * @date 2025-05-13
 */
@Tag(name = "排风计划", description = "排风计划")
@RestController
@RequestMapping("/system/exhaustSchedule")
public class AcwExhaustScheduleController extends BaseController
{
    @Autowired
    private IAcwExhaustScheduleService acwExhaustScheduleService;

    /**
     * 查询排风计划列表
     */
    @Operation(summary = "查询排风计划列表")
    @Parameters({
            @Parameter(name = "greenhouseId", description = "大棚的ID"),
            @Parameter(name = "status", description = "计划状态（1：有效0：无效）"),
            @Parameter(name = "pageSize", description = "每页数据条数"),
            @Parameter(name = "pageNum", description = "页码"),
            @Parameter(name = "orderByColumn", description = "排序数据库表字段名"),
            @Parameter(name = "isAsc", description = "是否升序，true为升序，false为降序"),
            @Parameter(name = "params", description = "创建时间的时间范围，开始时间和结束时间MAP类型",
                    example = "{\"beginCreateTime\":\"2025-03-14 10:00:00\",\"endCreateTime\":\"2025-04-21 10:00:00\"}")
    })
    @ApiResponse(useReturnTypeSchema = false, description = "返回排风记录列表", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "total", schema = @Schema(description = "数据总数", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "rows",
                                    array = @io.swagger.v3.oas.annotations.media.ArraySchema(schema = @Schema(description = "返回数据列表", implementation = AcwExhaustRecord.class)))
                    }
            )
    )
    @GetMapping("/list")
    public TableDataInfo list(@Schema(hidden = true) AcwExhaustSchedule acwExhaustSchedule)
    {
        startPage();
        List<AcwExhaustSchedule> list = acwExhaustScheduleService.selectAcwExhaustScheduleList(acwExhaustSchedule);
        return getDataTable(list);
    }

    /**
     * 导出排风计划列表
     */
    @Operation(summary = "导出排风计划列表")
    @Parameters({
            @Parameter(name = "greenhouseId", description = "大棚的ID"),
            @Parameter(name = "status", description = "计划状态（1：有效0：无效）"),
            @Parameter(name = "params", description = "创建时间的时间范围，开始时间和结束时间MAP类型",
                    example = "{\"beginCreateTime\":\"2025-03-14 10:00:00\",\"endCreateTime\":\"2025-04-21 10:00:00\"}")
    })
    @ApiResponse(useReturnTypeSchema = false, description = "导出排风计划列表", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "data", schema = @Schema(description = "生成的文件名", type = "String")),
                    }
            )
    )
    @PreAuthorize("@ss.hasPermi('system:exhaustSchedule:export')")
    @Log(title = "排风计划", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(@Schema(hidden = true) AcwExhaustSchedule acwExhaustSchedule)
    {
        List<AcwExhaustSchedule> list = acwExhaustScheduleService.selectAcwExhaustScheduleList(acwExhaustSchedule);
        ExcelUtil<AcwExhaustSchedule> util = new ExcelUtil<AcwExhaustSchedule>(AcwExhaustSchedule.class);
        return util.exportExcel(list, "排风计划数据");
    }

    /**
     * 获取排风计划详细信息
     */
    @Operation(summary = "获取排风计划详细信息")
    @Parameters({
            @Parameter(name = "id", description = "计划ID")
    })
    @ApiResponse(useReturnTypeSchema = false, description = "获取排风计划详细信息", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "data", schema = @Schema(description = "返回数据", implementation = AcwExhaustSchedule.class))
                    }
            )
    )
    @PreAuthorize("@ss.hasPermi('system:exhaustSchedule:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(acwExhaustScheduleService.selectAcwExhaustScheduleById(id));
    }

    /**
     * 新增排风计划
     */
    @Operation(summary = "新增排风计划")
    @Parameter(description = "排风计划实体", content = @io.swagger.v3.oas.annotations.media.Content(
            mediaType = "application/json",
            schema = @Schema(implementation = AcwExhaustSchedule.class)))
    @ApiResponse(useReturnTypeSchema = false, description = "返回结果JSON格式", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "data", schema = @Schema(description = "返回数据", implementation = AcwExhaustSchedule.class))
                    }
            )
    )
    @PreAuthorize("@ss.hasPermi('system:exhaustSchedule:add')")
    @Log(title = "排风计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AcwExhaustSchedule acwExhaustSchedule)
    {
        return toAjax(acwExhaustScheduleService.insertAcwExhaustSchedule(acwExhaustSchedule));
    }

    /**
     * 修改排风计划
     */
    @Operation(summary = "修改排风计划")
    @Parameter(description = "排风计划实体", content = @io.swagger.v3.oas.annotations.media.Content(
            mediaType = "application/json",
            schema = @Schema(implementation = AcwExhaustSchedule.class)))
    @ApiResponse(useReturnTypeSchema = false, description = "返回结果JSON格式", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "data", schema = @Schema(description = "返回数据", type = "Object"))
                    }
            )
    )
    @PreAuthorize("@ss.hasPermi('system:exhaustSchedule:edit')")
    @Log(title = "排风计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AcwExhaustSchedule acwExhaustSchedule)
    {
        return toAjax(acwExhaustScheduleService.updateAcwExhaustSchedule(acwExhaustSchedule));
    }

    /**
     * 删除排风计划
     */
    @Operation(summary = "删除排风计划")
    @Parameters({
            @Parameter(name = "ids", description = "计划ID列表")
    })
    @ApiResponse(useReturnTypeSchema = false, description = "返回结果JSON格式", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "data", schema = @Schema(description = "返回数据", type = "Object"))
                    }
            )
    )
    @PreAuthorize("@ss.hasPermi('system:exhaustSchedule:remove')")
    @Log(title = "排风计划", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(acwExhaustScheduleService.deleteAcwExhaustScheduleByIds(ids));
    }
}
