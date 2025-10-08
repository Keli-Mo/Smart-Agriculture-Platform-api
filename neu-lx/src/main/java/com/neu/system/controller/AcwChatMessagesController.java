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
import com.neu.system.domain.AcwChatMessages;
import com.neu.system.service.IAcwChatMessagesService;
import com.neu.common.utils.poi.ExcelUtil;
import com.neu.common.core.page.TableDataInfo;

/**
 * 聊天记录Controller
 * 
 * @author neu
 * @date 2025-05-14
 */
@Tag(name = "聊天记录", description = "聊天记录")
@RestController
@RequestMapping("/system/chatMessages")
public class AcwChatMessagesController extends BaseController
{
    @Autowired
    private IAcwChatMessagesService acwChatMessagesService;

    /**
     * 查询聊天记录列表
     */
    @Operation(summary = "查询聊天记录列表")
    @Parameters({
            @Parameter(name = "userId", description = "用户ID"),
            @Parameter(name = "role", description = "角色(user/assistant)"),
            @Parameter(name = "pageSize", description = "每页数据条数"),
            @Parameter(name = "pageNum", description = "页码"),
            @Parameter(name = "orderByColumn", description = "排序数据库表字段名"),
            @Parameter(name = "isAsc", description = "排序方式，asc升序，desc降序"),
            @Parameter(name = "params", description = "创建时间的时间范围，开始时间和结束时间MAP类型",
                    example = "{\"beginCreateTime\":\"2025-03-14 10:00:00\",\"endCreateTime\":\"2025-04-21 10:00:00\"}")
    })
    @ApiResponse(useReturnTypeSchema = false, description = "返回聊天记录列表", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "total", schema = @Schema(description = "数据总数", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "rows",
                                    array = @io.swagger.v3.oas.annotations.media.ArraySchema(schema = @Schema(description = "返回数据列表", implementation = AcwChatMessages.class)))
                    }
            )
    )
    @GetMapping("/list")
    public TableDataInfo list(@Schema(hidden = true) AcwChatMessages acwChatMessages)
    {
        startPage();
        List<AcwChatMessages> list = acwChatMessagesService.selectAcwChatMessagesList(acwChatMessages);
        return getDataTable(list);
    }

    /**
     * 导出聊天记录列表
     */
    @Operation(summary = "导出聊天记录列表")
    @Parameters({
            @Parameter(name = "userId", description = "用户ID"),
            @Parameter(name = "role", description = "角色(user/assistant)"),
            @Parameter(name = "pageSize", description = "每页数据条数"),
            @Parameter(name = "pageNum", description = "页码"),
            @Parameter(name = "orderByColumn", description = "排序数据库表字段名"),
            @Parameter(name = "isAsc", description = "排序方式，asc升序，desc降序"),
            @Parameter(name = "params", description = "创建时间的时间范围，开始时间和结束时间MAP类型",
                    example = "{\"beginCreateTime\":\"2025-03-14 10:00:00\",\"endCreateTime\":\"2025-04-21 10:00:00\"}")
    })
    @ApiResponse(useReturnTypeSchema = false, description = "导出聊天记录列表", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "data", schema = @Schema(description = "生成随机文件名", type = "String"))
                    }
            )
    )
    @PreAuthorize("@ss.hasPermi('system:chatMessages:export')")
    @Log(title = "聊天记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(@Schema(hidden = true)AcwChatMessages acwChatMessages)
    {
        List<AcwChatMessages> list = acwChatMessagesService.selectAcwChatMessagesList(acwChatMessages);
        ExcelUtil<AcwChatMessages> util = new ExcelUtil<AcwChatMessages>(AcwChatMessages.class);
        return util.exportExcel(list, "聊天记录数据");
    }

    /**
     * 获取聊天记录详细信息
     */
    @Operation(summary = "获取聊天记录详细信息")
    @Parameter(name = "id", description = "主键")
    @ApiResponse(useReturnTypeSchema = false, description = "返回结果JSON格式", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "data", schema = @Schema(description = "返回数据", implementation = AcwChatMessages.class))
                    }
            )
    )
    @PreAuthorize("@ss.hasPermi('system:chatMessages:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(acwChatMessagesService.selectAcwChatMessagesById(id));
    }

    /**
     * 新增聊天记录
     */
    @Operation(summary = "新增聊天记录")
    @Parameter(description = "聊天记录实体", content = @io.swagger.v3.oas.annotations.media.Content(
            mediaType = "application/json",
            schema = @Schema(implementation = AcwChatMessages.class)))
    @ApiResponse(useReturnTypeSchema = false, description = "返回结果JSON格式", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "data", schema = @Schema(description = "返回数据", type = "Object"))
                    }
            )
    )
    @Log(title = "聊天记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AcwChatMessages acwChatMessages)
    {
        return toAjax(acwChatMessagesService.insertAcwChatMessages(acwChatMessages));
    }

    /**
     * 修改聊天记录
     */
    @Operation(summary = "修改聊天记录")
    @Parameter(description = "聊天记录实体", content = @io.swagger.v3.oas.annotations.media.Content(
            mediaType = "application/json",
            schema = @Schema(implementation = AcwChatMessages.class)))
    @ApiResponse(useReturnTypeSchema = false, description = "返回结果JSON格式", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "data", schema = @Schema(description = "返回数据", type = "Object"))
                    }
            )
    )
    @PreAuthorize("@ss.hasPermi('system:chatMessages:edit')")
    @Log(title = "聊天记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AcwChatMessages acwChatMessages)
    {
        return toAjax(acwChatMessagesService.updateAcwChatMessages(acwChatMessages));
    }

    /**
     * 删除聊天记录
     */
    @Operation(summary = "删除聊天记录")
    @Parameter(name = "ids", description = "主键")
    @ApiResponse(useReturnTypeSchema = false, description = "返回结果JSON格式", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "data", schema = @Schema(description = "返回数据", type = "Object"))
                    }
            )
    )
    @PreAuthorize("@ss.hasPermi('system:chatMessages:remove')")
    @Log(title = "聊天记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(acwChatMessagesService.deleteAcwChatMessagesByIds(ids));
    }
}
