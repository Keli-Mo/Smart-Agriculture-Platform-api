package com.neu.system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.github.pagehelper.PageHelper;
import com.neu.common.annotation.Log;
import com.neu.common.core.controller.BaseController;
import com.neu.common.core.domain.AjaxResult;
import com.neu.common.enums.BusinessType;
import com.neu.common.utils.DateUtils;
import com.neu.common.utils.StringUtils;
import com.neu.system.ai.AgricultureAIService;
import com.neu.system.domain.*;
import com.neu.system.service.*;
import com.neu.system.vo.ChatMessageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Date;
import java.util.List;

/**
 * Open API接口
 */
@Tag(name = "Open API接口", description = "Open API接口")
@RestController
@RequestMapping("/api")
@Slf4j
public class AcwApiController extends BaseController {

    //数据更新频率5分钟
    public static final int MAX_DATA_INTERVAL = 5;
    @Autowired
    private IAcwDeviceInfoService acwDeviceInfoService;
    @Autowired
    private IAcwAlertLogService acwAlertLogService;
    @Autowired
    private IAcwGreenhouseMonitorService acwGreenhouseMonitorService;
    @Autowired
    private IAcwGreenhouseService acwGreenhouseService;
    @Autowired
    private IAcwMessagesService acwMessagesService;
    @Autowired
    private AgricultureAIService agricultureAIService;

    @Operation(summary = "上报设备信息", description = "上报设备信息")
    @Parameter(description = "大棚监测实体", content = @io.swagger.v3.oas.annotations.media.Content(
            mediaType = "application/json",
            schema = @Schema(implementation = AcwGreenhouseMonitor.class)))
    @ApiResponse(useReturnTypeSchema = false, description = "返回结果JSON格式", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "data", schema = @Schema(description = "返回数据", type = "Object"))
                    }
            )
    )
    @Log(title = "上报环境监测数据", businessType = BusinessType.INSERT)
    @PostMapping("/greenhouse/monitor")
    public AjaxResult saveGreenhouseMoniter(@RequestBody AcwGreenhouseMonitor acwGreenhouseMonitor) {
        //替换特殊字符
        String replaceNo = convertDeviceNo(acwGreenhouseMonitor.getDeviceNo());
        acwGreenhouseMonitor.setDeviceNo(replaceNo);
        //获取最新数据
        AcwGreenhouseMonitor filter = new AcwGreenhouseMonitor();
        filter.setGhId(acwGreenhouseMonitor.getGhId());
        PageHelper.startPage(1, 1, "data_time desc");
        List<AcwGreenhouseMonitor> list = acwGreenhouseMonitorService.selectAcwGreenhouseMonitorList(filter);
        if (!list.isEmpty()) {
            //5分钟之内只保存一条最新数据
            AcwGreenhouseMonitor old = list.get(0);
            //忽略非法数据
            if (old.getDataTime() == null) {
                return toAjax(acwGreenhouseMonitorService.insertAcwGreenhouseMonitor(acwGreenhouseMonitor));
            }
            Date now = new Date();
            long diff = now.getTime() - old.getDataTime().getTime();
            //小于5分钟不保存
            if (diff < MAX_DATA_INTERVAL * 60 * 1000) {
                return success();
            } else {
                return toAjax(acwGreenhouseMonitorService.insertAcwGreenhouseMonitor(acwGreenhouseMonitor));
            }
        } else {
            return toAjax(acwGreenhouseMonitorService.insertAcwGreenhouseMonitor(acwGreenhouseMonitor));
        }
    }

    @Operation(summary = "上报大棚预警信息", description = "上报大棚预警信息")
    @Parameter(description = "大棚预警实体", content = @io.swagger.v3.oas.annotations.media.Content(
            mediaType = "application/json",
            schema = @Schema(implementation = AcwAlertLog.class)))
    @ApiResponse(useReturnTypeSchema = false, description = "返回结果JSON格式", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "data", schema = @Schema(description = "返回数据", type = "Object"))
                    }
            )
    )
    @Log(title = "上报监测预警数据", businessType = BusinessType.INSERT)
    @PostMapping("/greenhouse/alert")
    public AjaxResult savePoolAlert(@RequestBody AcwAlertLog acwAlertLog) {
        //替换特殊字符
        String replaceNo = convertDeviceNo(acwAlertLog.getDeviceNo());
        acwAlertLog.setDeviceNo(replaceNo);
        //手动预警
        if ("2".equals(acwAlertLog.getType())) {
            return toAjax(acwAlertLogService.insertAcwAlertLog(acwAlertLog));
        }
        //获取最新数据
        AcwAlertLog filter = new AcwAlertLog();
        filter.setGhId(acwAlertLog.getGhId());
        PageHelper.startPage(1, 1, "create_time desc");
        List<AcwAlertLog> list = acwAlertLogService.selectAcwAlertLogList(filter);
        if (!list.isEmpty()) {
            //5分钟之内只保存一条最新数据
            AcwAlertLog old = list.get(0);
            //忽略非法数据
            if (old.getCreateTime() == null) {
                return toAjax(acwAlertLogService.insertAcwAlertLog(acwAlertLog));
            }
            Date now = new Date();
            long diff = now.getTime() - old.getCreateTime().getTime();
            //小于5分钟不保存
            if (diff < MAX_DATA_INTERVAL * 60 * 1000) {
                return success();
            } else {
                return toAjax(acwAlertLogService.insertAcwAlertLog(acwAlertLog));
            }
        } else {
            return toAjax(acwAlertLogService.insertAcwAlertLog(acwAlertLog));
        }
    }

    @Operation(summary = "上报大棚地理位置", description = "上报大棚地理位置")
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
    @Log(title = "上报大棚地理位置", businessType = BusinessType.INSERT)
    @PostMapping("/greenhouse/location")
    public AjaxResult saveGreenHouseLocation(@RequestBody AcwGreenhouse greenhouse) {
        AcwGreenhouse old = acwGreenhouseService.selectAcwGreenhouseById(greenhouse.getId());
        old.setLng(greenhouse.getLng());
        old.setLat(greenhouse.getLat());
        return toAjax(acwGreenhouseService.updateAcwGreenhouse(old));
    }

    @Operation(summary = "更新设备状态", description = "更新设备状态")
    @Parameter(description = "大棚实体", content = @io.swagger.v3.oas.annotations.media.Content(
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
    @Log(title = "更新设备状态", businessType = BusinessType.INSERT)
    @PostMapping("/device/status")
    public AjaxResult updateDevice(@RequestBody AcwDeviceInfo acwDeviceInfo) {
        //替换特殊字符
        String replaceNo = convertDeviceNo(acwDeviceInfo.getSerialNo());
        acwDeviceInfo.setSerialNo(replaceNo);

        AcwDeviceInfo info = new AcwDeviceInfo();
        info.setSerialNo(acwDeviceInfo.getSerialNo());
        List<AcwDeviceInfo> list = acwDeviceInfoService.selectAcwDeviceInfoList(info);
        if (!list.isEmpty()) {
            AcwDeviceInfo old = list.get(0);
            old.setStatus(acwDeviceInfo.getStatus());
            return toAjax(acwDeviceInfoService.updateAcwDeviceInfo(old));
        } else {

            return toAjax(acwDeviceInfoService.insertAcwDeviceInfo(acwDeviceInfo));
        }
    }

    /**
     * 获取最新的排风的消息并标记已读
     *
     * @param greenhouseId
     * @return
     */
    @Operation(summary = "获取最新的排风的消息并标记已读", description = "获取最新的排风的消息并标记已读")
    @Parameter(name = "greenhouseId", description = "大棚ID")
    @ApiResponse(useReturnTypeSchema = false, description = "返回结果JSON格式", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "data", schema = @Schema(description = "返回数据", implementation = AcwMessages.class))
                    }
            )
    )
    @GetMapping("/msg/exhaust/{greenhouseId}")
    public AjaxResult getLatestEnableExhaustMsg(@PathVariable Long greenhouseId) {
        Date currentTime = new Date();
        String start = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, currentTime) + " 00:00:00";
        String end = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, currentTime) + " 23:59:59";

        AcwMessages filter2 = new AcwMessages();
        filter2.setReceiver(String.valueOf(greenhouseId));
        filter2.setTopic("greenhouse.exhaust.msg");
        filter2.getParams().put("beginSendTime", start);
        filter2.getParams().put("endSendTime", end);
        PageHelper.startPage(1, 1, "send_time desc");
        List<AcwMessages> acwMessages = acwMessagesService.selectAcwMessagesList(filter2);
        if (acwMessages.isEmpty()) {
            return success();
        }
        AcwMessages msg = acwMessages.get(0);
        if (msg.getStatus().equals("1")) {
            return success();
        }

        //判断消息是否是当前时间发送
        String now = DateUtils.parseDateToStr("yyyy-MM-dd HH:mm", currentTime);

        if (!now.equals(msg.getMsgBody())) {
            return success();
        }

        //标记为已读
        msg.setStatus("1");
        msg.setReceiveTime(new Date());
        acwMessagesService.updateAcwMessages(msg);

        return AjaxResult.success(msg);
    }

    /**
     * 读取消息
     *
     * @param msgId
     * @return
     */
    @Operation(summary = "读取消息", description = "读取消息")
    @Parameter(name = "msgId", description = "消息ID")
    @ApiResponse(useReturnTypeSchema = false, description = "返回结果JSON格式", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "data", schema = @Schema(description = "返回数据", type = "Object"))
                    }
            )
    )
    @PostMapping("/msg/read/{msgId}")
    public AjaxResult readMsg(@PathVariable Long msgId) {
        AcwMessages msg = acwMessagesService.selectAcwMessagesById(msgId);
        if (msg != null) {
            msg.setStatus("1");
            msg.setReceiveTime(new Date());
            acwMessagesService.updateAcwMessages(msg);
        }
        return success();
    }

    /**
     * 创建聊天会话
     * @param chatMessageVO
     * @return
     */
    @Operation(summary = "创建聊天会话", description = "创建聊天会话")
    @Parameter(description = "聊天消息实体", content = @io.swagger.v3.oas.annotations.media.Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ChatMessageVO.class)))
    @ApiResponse(useReturnTypeSchema = false, description = "返回结果JSON格式", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "data", schema = @Schema(description = "返回数据", type = "Object"))
                    }
            )
    )
    @GetMapping(path="/chat/stream",produces = "text/event-stream")
    public SseEmitter createStreamChat(ChatMessageVO chatMessageVO) {
        try {
            return agricultureAIService.createChatStream(chatMessageVO.getUserId(), chatMessageVO.getQuestion());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //替换设备编号中的特殊字符
    private String convertDeviceNo(String deviceNo) {
        if (StringUtils.isBlank(deviceNo)) {
            return deviceNo;
        } else {
            return StringUtils.replace(deviceNo, ":", "");
        }
    }
}
