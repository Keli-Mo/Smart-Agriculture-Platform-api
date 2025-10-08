package com.neu.system.controller;

import com.neu.common.annotation.Log;
import com.neu.common.core.controller.BaseController;
import com.neu.common.core.domain.AjaxResult;
import com.neu.common.core.page.TableDataInfo;
import com.neu.common.enums.BusinessType;
import com.neu.common.utils.poi.ExcelUtil;
import com.neu.system.domain.AcwAlertLog;
import com.neu.system.domain.AcwGreenhouse;
import com.neu.system.service.IAcwAlertLogService;
import com.neu.system.service.IAcwGreenhouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 预警记录Controller
 *
 * @author neu
 * @date 2025-03-25
 */
@Tag(name = "预警记录", description = "预警记录")
@RestController
@RequestMapping("/system/alertLog")
public class AcwAlertLogController extends BaseController {
    @Autowired
    private IAcwAlertLogService acwAlertLogService;
    @Autowired
    private IAcwGreenhouseService acwPoolService;


    /**
     * 查询预警记录列表
     */
    @Operation(summary = "查询预警记录列表")
    @Parameters({
            @Parameter(name = "ghId", description = "大棚ID"),
            @Parameter(name = "deviceNo", description = "设备编号"),
            @Parameter(name = "pageSize", description = "每页数据条数"),
            @Parameter(name = "pageNum", description = "页码"),
            @Parameter(name = "orderByColumn", description = "排序数据库表字段名"),
            @Parameter(name = "isAsc", description = "排序方式，asc升序，desc降序"),
            @Parameter(name = "params", description = "创建时间的时间范围，开始时间和结束时间MAP类型",
                    example = "{\"beginCreateTime\":\"2025-03-14 10:00:00\",\"endCreateTime\":\"2025-04-21 10:00:00\"}")
    })
    @ApiResponse(useReturnTypeSchema = false, description = "返回预警记录列表", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "total", schema = @Schema(description = "数据总数", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "rows",
                                    array = @io.swagger.v3.oas.annotations.media.ArraySchema(schema = @Schema(description = "返回数据列表", implementation = AcwAlertLog.class)))
                    }
            )
    )
    @GetMapping("/list")
    public TableDataInfo list(@Schema(hidden = true) AcwAlertLog acwAlertLog) {
        startPage();
        List<AcwAlertLog> list = acwAlertLogService.selectAcwAlertLogList(acwAlertLog);
        list.forEach(log -> {
            AcwGreenhouse greenhouse = acwPoolService.selectAcwGreenhouseById(log.getGhId());
            log.setGreenhouse(greenhouse);
        });
        return getDataTable(list);
    }

    /**
     * 导出预警记录列表
     */
    @Operation(summary = "导出预警记录列表")
    @Parameters({
            @Parameter(name = "ghId", description = "大棚ID"),
            @Parameter(name = "deviceNo", description = "设备编号"),
            @Parameter(name = "params", description = "创建时间的时间范围，开始时间和结束时间MAP类型",
                    example = "{\"beginCreateTime\":\"2025-03-14 10:00:00\",\"endCreateTime\":\"2025-04-21 10:00:00\"}")
    })
    @ApiResponse(useReturnTypeSchema = false, description = "返回结果JSON格式", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "data", schema = @Schema(description = "生成的文件名", type = "String"))
                    }
            )
    )
    @PreAuthorize("@ss.hasPermi('system:alertLog:export')")
    @Log(title = "预警记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(@Schema(hidden = true)AcwAlertLog acwAlertLog) {
        List<AcwAlertLog> list = acwAlertLogService.selectAcwAlertLogList(acwAlertLog);
        ExcelUtil<AcwAlertLog> util = new ExcelUtil<AcwAlertLog>(AcwAlertLog.class);
        return util.exportExcel(list, "预警记录数据");
    }

    /**
     * 获取预警记录详细信息
     */
    @Operation(summary = "获取预警记录详细信息")
    @Parameter(name = "id", description = "预警记录ID")
    @ApiResponse(useReturnTypeSchema = false, description = "返回结果JSON格式", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "data", schema = @Schema(description = "返回数据", implementation = AcwAlertLog.class))
                    }
            )
    )
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        AcwAlertLog log = acwAlertLogService.selectAcwAlertLogById(id);
        AcwGreenhouse greenhouse = acwPoolService.selectAcwGreenhouseById(log.getGhId());
        log.setGreenhouse(greenhouse);
        return AjaxResult.success(log);
    }

    /**
     * 新增预警记录
     */
    @Operation(summary = "新增预警记录")
    @Parameter(description = "预警记录实体", content = @io.swagger.v3.oas.annotations.media.Content(
            mediaType = "application/json",
            schema = @Schema(implementation = AcwAlertLog.class))
    )
    @ApiResponse(useReturnTypeSchema = false, description = "返回结果JSON格式", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "data", schema = @Schema(description = "返回数据", implementation = AcwAlertLog.class))
                    }
            )
    )
    @PreAuthorize("@ss.hasPermi('system:alertLog:add')")
    @Log(title = "预警记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AcwAlertLog acwAlertLog) {
        return toAjax(acwAlertLogService.insertAcwAlertLog(acwAlertLog));
    }

    /**
     * 修改预警记录
     */
    @Operation(summary = "修改预警记录")
    @Parameter(description = "预警记录实体", content = @io.swagger.v3.oas.annotations.media.Content(
            mediaType = "application/json",
            schema = @Schema(implementation = AcwAlertLog.class))
    )
    @ApiResponse(useReturnTypeSchema = false, description = "返回结果JSON格式", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "data", schema = @Schema(description = "返回数据", implementation = AcwAlertLog.class))
                    }
            )
    )
    @PreAuthorize("@ss.hasPermi('system:alertLog:edit')")
    @Log(title = "预警记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AcwAlertLog acwAlertLog) {
        return toAjax(acwAlertLogService.updateAcwAlertLog(acwAlertLog));
    }

    /**
     * 删除预警记录
     */
    @Operation(summary = "删除预警记录")
    @Parameter(name = "ids", description = "预警记录ID")
    @ApiResponse(useReturnTypeSchema = false, description = "返回结果JSON格式", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "data", schema = @Schema(description = "返回数据", type = "int"))
                    }
            )
    )
    @PreAuthorize("@ss.hasPermi('system:alertLog:remove')")
    @Log(title = "预警记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(acwAlertLogService.deleteAcwAlertLogByIds(ids));
    }
}
