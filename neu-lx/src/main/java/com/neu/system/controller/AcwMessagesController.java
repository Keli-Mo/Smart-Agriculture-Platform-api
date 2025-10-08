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
import com.neu.system.domain.AcwMessages;
import com.neu.system.service.IAcwMessagesService;
import com.neu.common.utils.poi.ExcelUtil;
import com.neu.common.core.page.TableDataInfo;

/**
 * 消息Controller
 * 
 * @author neu
 * @date 2025-05-13
 */
@Tag(name = "消息", description = "消息")
@RestController
@RequestMapping("/system/messages")
public class AcwMessagesController extends BaseController
{
    @Autowired
    private IAcwMessagesService acwMessagesService;

    /**
     * 查询消息列表
     */
    @Operation(summary = "查询消息列表")
    @Parameters({
            @Parameter(name = "pageSize", description = "每页数据条数"),
            @Parameter(name = "pageNum", description = "页码"),
            @Parameter(name = "orderByColumn", description = "排序数据库表字段名"),
            @Parameter(name = "isAsc", description = "排序方式，asc升序，desc降序"),
            @Parameter(name = "topic", description = "消息主题"),
            @Parameter(name = "sender", description = "消息发送者"),
            @Parameter(name = "receiver", description = "消息接收者"),
            @Parameter(name = "status", description = "消息状态"),
            @Parameter(name = "params", description = "时间范围，开始时间和结束时间MAP类型",
                    example = "{" +
                            "\"beginSendTime\":\"2025-03-14 10:00:00（发送开始时间）\"," +
                            "\"beginSendTime\":\"2025-04-21 10:00:00（发送结束时间）\"" +
                            "\"beginReceiveTime\":\"2025-04-21 10:00:00（接收开始时间）\"" +
                            "\"endReceiveTime\":\"2025-04-21 10:00:00（接收结束时间）\"" +
                            "}")
    })
    @ApiResponse(useReturnTypeSchema = false, description = "返回消息列表", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "total", schema = @Schema(description = "数据总数", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "rows",
                                    array = @io.swagger.v3.oas.annotations.media.ArraySchema(schema = @Schema(description = "返回数据列表", implementation = AcwMessages.class)))
                    }
            )
    )
    @PreAuthorize("@ss.hasPermi('system:messages:list')")
    @GetMapping("/list")
    public TableDataInfo list(@Schema(hidden = true)AcwMessages acwMessages)
    {
        startPage();
        List<AcwMessages> list = acwMessagesService.selectAcwMessagesList(acwMessages);
        return getDataTable(list);
    }

    /**
     * 导出消息列表
     */
    @Operation(summary = "导出消息列表")
    @Parameters({
            @Parameter(name = "topic", description = "消息主题"),
            @Parameter(name = "sender", description = "消息发送者"),
            @Parameter(name = "receiver", description = "消息接收者"),
            @Parameter(name = "status", description = "消息状态"),
            @Parameter(name = "params", description = "时间范围，开始时间和结束时间MAP类型",
                    example = "{" +
                            "\"beginSendTime\":\"2025-03-14 10:00:00（发送开始时间）\"," +
                            "\"beginSendTime\":\"2025-04-21 10:00:00（发送结束时间）\"" +
                            "\"beginReceiveTime\":\"2025-04-21 10:00:00（接收开始时间）\"" +
                            "\"endReceiveTime\":\"2025-04-21 10:00:00（接收结束时间）\"" +
                            "}")
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
    @PreAuthorize("@ss.hasPermi('system:messages:export')")
    @Log(title = "消息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(@Schema(hidden = true)AcwMessages acwMessages)
    {
        List<AcwMessages> list = acwMessagesService.selectAcwMessagesList(acwMessages);
        ExcelUtil<AcwMessages> util = new ExcelUtil<AcwMessages>(AcwMessages.class);
        return util.exportExcel(list, "消息数据");
    }

    /**
     * 获取消息详细信息
     */
    @Operation(summary = "获取消息详细信息")
    @Parameter(name = "id", description = "消息ID")
    @ApiResponse(useReturnTypeSchema = false, description = "返回结果JSON格式", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "data", schema = @Schema(description = "返回数据", implementation = AcwMessages.class))
                    }
            )
    )
    @PreAuthorize("@ss.hasPermi('system:messages:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(acwMessagesService.selectAcwMessagesById(id));
    }

    /**
     * 新增消息
     */
    @Operation(summary = "新增消息")
    @Parameter(description = "消息实体", content = @io.swagger.v3.oas.annotations.media.Content(
            mediaType = "application/json",
            schema = @Schema(implementation = AcwMessages.class)))
    @ApiResponse(useReturnTypeSchema = false, description = "返回结果JSON格式", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "data", schema = @Schema(description = "返回数据", type = "Object"))
                    }
            )
    )
    @PreAuthorize("@ss.hasPermi('system:messages:add')")
    @Log(title = "消息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AcwMessages acwMessages)
    {
        return toAjax(acwMessagesService.insertAcwMessages(acwMessages));
    }

    /**
     * 修改消息
     */
    @Operation(summary = "修改消息")
    @Parameter(description = "消息实体", content = @io.swagger.v3.oas.annotations.media.Content(
            mediaType = "application/json",
            schema = @Schema(implementation = AcwMessages.class)))
    @ApiResponse(useReturnTypeSchema = false, description = "返回结果JSON格式", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "data", schema = @Schema(description = "返回数据", type = "Object"))
                    }
            )
    )
    @PreAuthorize("@ss.hasPermi('system:messages:edit')")
    @Log(title = "消息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AcwMessages acwMessages)
    {
        return toAjax(acwMessagesService.updateAcwMessages(acwMessages));
    }

    /**
     * 删除消息
     */
    @Operation(summary = "删除消息")
    @Parameter(name = "ids", description = "消息ID数组",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    array = @io.swagger.v3.oas.annotations.media.ArraySchema(schema = @Schema(description = "消息ID", type = "Long"))
            ))
    @ApiResponse(useReturnTypeSchema = false, description = "返回结果JSON格式", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "data", schema = @Schema(description = "返回数据", type = "Object"))
                    }
            )
    )
    @PreAuthorize("@ss.hasPermi('system:messages:remove')")
    @Log(title = "消息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(acwMessagesService.deleteAcwMessagesByIds(ids));
    }
}
