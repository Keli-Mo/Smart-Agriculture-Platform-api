package com.neu.system.controller;

import com.neu.common.annotation.Log;
import com.neu.common.core.controller.BaseController;
import com.neu.common.core.domain.AjaxResult;
import com.neu.common.core.page.TableDataInfo;
import com.neu.common.enums.BusinessType;
import com.neu.common.utils.poi.ExcelUtil;
import com.neu.system.domain.AcwArticle;
import com.neu.system.service.IAcwArticleService;
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
 * 文章管理Controller
 *
 * @author neu
 * @date 2025-03-14
 */
@Tag(name = "文章管理", description = "文章管理")
@RestController
@RequestMapping("/system/article")
public class AcwArticleController extends BaseController {
    @Autowired
    private IAcwArticleService acwArticleService;

    /**
     * 查询文章管理列表
     */
    @Operation(summary = "查询文章管理列表")
    @Parameters({
            @Parameter(name = "title", description = "标题"),
            @Parameter(name = "pageSize", description = "每页数据条数"),
            @Parameter(name = "pageNum", description = "页码"),
            @Parameter(name = "orderByColumn", description = "排序数据库表字段名"),
            @Parameter(name = "isAsc", description = "排序方式，asc升序，desc降序"),
            @Parameter(name = "title", description = "标题"),
            @Parameter(name = "params", description = "创建时间的时间范围，开始时间和结束时间MAP类型",
                    example = "{\"beginCreateTime\":\"2025-03-14 10:00:00\",\"endCreateTime\":\"2025-04-21 10:00:00\"}")
    })
    @ApiResponse(useReturnTypeSchema = false, description = "返回文章管理列表", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "total", schema = @Schema(description = "数据总数", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "rows",
                                    array = @io.swagger.v3.oas.annotations.media.ArraySchema(schema = @Schema(description = "返回数据列表", implementation = AcwArticle.class)))
                    }
            )
    )
    @GetMapping("/list")
    public TableDataInfo list(@Schema(hidden = true) AcwArticle acwArticle) {
        startPage();
        List<AcwArticle> list = acwArticleService.selectAcwArticleList(acwArticle);
        return getDataTable(list);
    }

    /**
     * 导出文章管理列表
     */
    @Operation(summary = "导出文章列表")
    @Parameters({
            @Parameter(name = "title", description = "标题"),
            @Parameter(name = "params", description = "创建时间的时间范围，开始时间和结束时间MAP类型",
                    example = "{\"beginCreateTime\":\"2025-03-14 10:00:00\",\"endCreateTime\":\"2025-04-21 10:00:00\"}")
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
    @PreAuthorize("@ss.hasPermi('system:article:export')")
    @Log(title = "文章管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(@Schema(hidden = true) AcwArticle acwArticle) {
        List<AcwArticle> list = acwArticleService.selectAcwArticleList(acwArticle);
        ExcelUtil<AcwArticle> util = new ExcelUtil<AcwArticle>(AcwArticle.class);
        return util.exportExcel(list, "文章管理数据");
    }

    /**
     * 获取文章管理详细信息
     */
    @Operation(summary = "获取文章详细信息")
    @Parameter(name = "id", description = "文章ID")
    @ApiResponse(useReturnTypeSchema = false, description = "返回结果JSON格式", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "data", schema = @Schema(description = "返回数据", implementation = AcwArticle.class))
                    },
                    mediaType = "application/json"))
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(acwArticleService.selectAcwArticleById(id));
    }

    /**
     * 新增文章管理
     */
    @Operation(summary = "新增文章")
    @Parameter(description = "文章实体", content = @io.swagger.v3.oas.annotations.media.Content(
            mediaType = "application/json",
            schema = @Schema(implementation = AcwArticle.class)))
    @ApiResponse(useReturnTypeSchema = false, description = "返回结果JSON格式", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "data", schema = @Schema(description = "返回数据", type = "Object"))
                    },
                    mediaType = "application/json"))
    @PreAuthorize("@ss.hasPermi('system:article:add')")
    @Log(title = "文章管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AcwArticle acwArticle) {
        return toAjax(acwArticleService.insertAcwArticle(acwArticle));
    }

    /**
     * 修改文章管理
     */
    @Operation(summary = "修改文章管理")
    @Parameter(description = "文章实体", content = @io.swagger.v3.oas.annotations.media.Content(
            mediaType = "application/json",
            schema = @Schema(implementation = AcwArticle.class)))
    @ApiResponse(useReturnTypeSchema = false, description = "返回结果JSON格式", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "data", schema = @Schema(description = "返回数据", type = "Object"))
                    }
            )
    )
    @PreAuthorize("@ss.hasPermi('system:article:edit')")
    @Log(title = "文章管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AcwArticle acwArticle) {
        return toAjax(acwArticleService.updateAcwArticle(acwArticle));
    }

    /**
     * 删除文章管理
     */
    @Operation(summary = "删除文章")
    @Parameter(name = "ids", description = "文章ID")
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
    @PreAuthorize("@ss.hasPermi('system:article:remove')")
    @Log(title = "文章管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(acwArticleService.deleteAcwArticleByIds(ids));
    }
}
