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
import com.neu.system.domain.AcwGreenhouse;
import com.neu.system.service.IAcwGreenhouseService;
import com.neu.common.utils.poi.ExcelUtil;
import com.neu.common.core.page.TableDataInfo;

/**
 * 大棚信息Controller
 * 
 * @author neu
 * @date 2025-03-26
 */
@Tag(name = "大棚信息", description = "大棚信息")
@RestController
@RequestMapping("/system/greenhouse")
public class AcwGreenhouseController extends BaseController
{
    @Autowired
    private IAcwGreenhouseService acwGreenhouseService;

    /**
     * 查询大棚信息列表
     */
    @Operation(summary = "查询大棚信息列表")
    @Parameters({
            @Parameter(name = "pageSize", description = "每页数据条数"),
            @Parameter(name = "pageNum", description = "页码"),
            @Parameter(name = "orderByColumn", description = "排序数据库表字段名"),
            @Parameter(name = "isAsc", description = "排序方式，asc升序，desc降序"),
            @Parameter(name = "name", description = "大棚名称"),
    })
    @ApiResponse(useReturnTypeSchema = false, description = "返回大棚信息列表", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "total", schema = @Schema(description = "数据总数", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "rows",
                                    array = @io.swagger.v3.oas.annotations.media.ArraySchema(schema = @Schema(description = "返回数据列表", implementation = AcwGreenhouse.class)))
                    }
            )
    )
    @GetMapping("/list")
    public TableDataInfo list(@Schema(hidden = true)AcwGreenhouse acwGreenhouse)
    {
        startPage();
        List<AcwGreenhouse> list = acwGreenhouseService.selectAcwGreenhouseList(acwGreenhouse);
        return getDataTable(list);
    }

    /**
     * 导出大棚信息列表
     */
    @Operation(summary = "导出大棚信息列表")
    @Parameters({
            @Parameter(name = "name", description = "大棚名称"),
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
    @PreAuthorize("@ss.hasPermi('system:greenhouse:export')")
    @Log(title = "大棚信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(@Schema(hidden = true)AcwGreenhouse acwGreenhouse)
    {
        List<AcwGreenhouse> list = acwGreenhouseService.selectAcwGreenhouseList(acwGreenhouse);
        ExcelUtil<AcwGreenhouse> util = new ExcelUtil<AcwGreenhouse>(AcwGreenhouse.class);
        return util.exportExcel(list, "大棚信息数据");
    }

    /**
     * 获取大棚信息详细信息
     */
    @Operation(summary = "获取大棚信息详细信息")
    @Parameter(name = "id", description = "大棚ID")
    @ApiResponse(useReturnTypeSchema = false, description = "返回结果JSON格式", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "data", schema = @Schema(description = "返回数据", implementation = AcwGreenhouse.class))
                    }
            )
    )
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(acwGreenhouseService.selectAcwGreenhouseById(id));
    }

    /**
     * 新增大棚信息
     */
    @Operation(summary = "新增大棚信息")
    @Parameter(description = "大棚实体", content = @io.swagger.v3.oas.annotations.media.Content(
            mediaType = "application/json",
            schema = @Schema(implementation = AcwGreenhouse.class)))
    @ApiResponse(useReturnTypeSchema = false, description = "返回结果JSON格式", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "data", schema = @Schema(description = "返回数据", type = "Object"))
                    }
            )
    )
    @Log(title = "大棚信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AcwGreenhouse acwGreenhouse)
    {
        return toAjax(acwGreenhouseService.insertAcwGreenhouse(acwGreenhouse));
    }

    /**
     * 修改大棚信息
     */
    @Operation(summary = "修改大棚信息")
    @Parameter(description = "大棚实体", content = @io.swagger.v3.oas.annotations.media.Content(
            mediaType = "application/json",
            schema = @Schema(implementation = AcwGreenhouse.class)))
    @ApiResponse(useReturnTypeSchema = false, description = "返回结果JSON格式", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "data", schema = @Schema(description = "返回数据", type = "Object"))
                    }
            )
    )
    @Log(title = "大棚信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AcwGreenhouse acwGreenhouse)
    {
        return toAjax(acwGreenhouseService.updateAcwGreenhouse(acwGreenhouse));
    }

    /**
     * 删除大棚信息
     */
    @Operation(summary = "删除大棚信息")
    @Parameter(name = "ids", description = "大棚ID数组")
    @ApiResponse(useReturnTypeSchema = false, description = "返回结果JSON格式", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "data", schema = @Schema(description = "返回数据", type = "Object"))
                    }
            )
    )
    @Log(title = "大棚信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(acwGreenhouseService.deleteAcwGreenhouseByIds(ids));
    }
}
