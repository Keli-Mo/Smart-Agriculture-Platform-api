package com.neu.system.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import cn.hutool.core.util.NumberUtil;
import com.github.pagehelper.PageHelper;
import com.neu.common.utils.DateUtils;
import com.neu.common.utils.StringUtils;
import com.neu.system.domain.AcwGreenhouse;
import com.neu.system.service.IAcwGreenhouseService;
import com.neu.system.vo.GreenHouseReportVO;
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
import com.neu.system.domain.AcwGreenhouseMonitor;
import com.neu.system.service.IAcwGreenhouseMonitorService;
import com.neu.common.utils.poi.ExcelUtil;
import com.neu.common.core.page.TableDataInfo;

/**
 * 大棚监测Controller
 * 
 * @author neu
 * @date 2025-03-26
 */
@Tag(name = "大棚监测", description = "大棚监测")
@RestController
@RequestMapping("/system/ghMonitor")
public class AcwGreenhouseMonitorController extends BaseController
{
    @Autowired
    private IAcwGreenhouseMonitorService acwGreenhouseMonitorService;
    @Autowired
    private IAcwGreenhouseService acwGreenhouseService;

    /**
     * 查询大棚监测列表
     */
    @Operation(summary = "查询大棚监测列表")
    @Parameters({
            @Parameter(name = "pageSize", description = "每页数据条数"),
            @Parameter(name = "pageNum", description = "页码"),
            @Parameter(name = "orderByColumn", description = "排序数据库表字段名"),
            @Parameter(name = "isAsc", description = "排序方式，asc升序，desc降序"),
            @Parameter(name = "ghId", description = "大棚ID"),
            @Parameter(name = "deviceNo", description = "开始时间"),
            @Parameter(name = "params", description = "监测时间的时间范围，开始时间和结束时间MAP类型",
                    example = "{\"beginDataTime\":\"2025-03-14 10:00:00\",\"endDataTime\":\"2025-04-21 10:00:00\"}")
    })
    @ApiResponse(useReturnTypeSchema = false, description = "返回大棚监测列表", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "total", schema = @Schema(description = "数据总数", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "rows",
                                    array = @io.swagger.v3.oas.annotations.media.ArraySchema(schema = @Schema(description = "返回数据列表", implementation = AcwGreenhouseMonitor.class)))
                    }
            )
    )
    @GetMapping("/list")
    public TableDataInfo list(AcwGreenhouseMonitor acwGreenhouseMonitor)
    {
        startPage();
        List<AcwGreenhouseMonitor> list = acwGreenhouseMonitorService.selectAcwGreenhouseMonitorList(acwGreenhouseMonitor);
        list.forEach(monitor ->{
            AcwGreenhouse greenhouse = acwGreenhouseService.selectAcwGreenhouseById(monitor.getGhId());
            monitor.setGreenhouse(greenhouse);
        });
        return getDataTable(list);
    }

    /**
     * 导出大棚监测列表
     */
    @Operation(summary = "导出大棚监测列表")
    @Parameters({
            @Parameter(name = "ghId", description = "大棚ID"),
            @Parameter(name = "deviceNo", description = "开始时间"),
            @Parameter(name = "params", description = "监测时间的时间范围，开始时间和结束时间MAP类型",
                    example = "{\"beginDataTime\":\"2025-03-14 10:00:00\",\"endDataTime\":\"2025-04-21 10:00:00\"}")
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
    @PreAuthorize("@ss.hasPermi('system:ghMonitor:export')")
    @Log(title = "大棚监测", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(AcwGreenhouseMonitor acwGreenhouseMonitor)
    {
        List<AcwGreenhouseMonitor> list = acwGreenhouseMonitorService.selectAcwGreenhouseMonitorList(acwGreenhouseMonitor);
        ExcelUtil<AcwGreenhouseMonitor> util = new ExcelUtil<AcwGreenhouseMonitor>(AcwGreenhouseMonitor.class);
        return util.exportExcel(list, "大棚监测数据");
    }

    /**
     * 获取大棚监测详细信息
     */
    @Operation(summary = "获取大棚监测详细信息")
    @Parameter(name = "id", description = "大棚监测ID")
    @ApiResponse(useReturnTypeSchema = false, description = "返回结果JSON格式", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "data", schema = @Schema(description = "返回数据", implementation = AcwGreenhouseMonitor.class))
                    }
            )
    )
    @PreAuthorize("@ss.hasPermi('system:ghMonitor:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        AcwGreenhouseMonitor monitor = acwGreenhouseMonitorService.selectAcwGreenhouseMonitorById(id);
        if(monitor != null){
            AcwGreenhouse greenhouse = acwGreenhouseService.selectAcwGreenhouseById(monitor.getGhId());
            monitor.setGreenhouse(greenhouse);
        }
        return AjaxResult.success(monitor);
    }

    /**
     * 新增大棚监测
     */
    @Operation(summary = "新增大棚监测")
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
    @PreAuthorize("@ss.hasPermi('system:ghMonitor:add')")
    @Log(title = "大棚监测", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AcwGreenhouseMonitor acwGreenhouseMonitor)
    {
        return toAjax(acwGreenhouseMonitorService.insertAcwGreenhouseMonitor(acwGreenhouseMonitor));
    }

    /**
     * 修改大棚监测
     */
    @Operation(summary = "修改大棚监测")
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
    @PreAuthorize("@ss.hasPermi('system:ghMonitor:edit')")
    @Log(title = "大棚监测", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AcwGreenhouseMonitor acwGreenhouseMonitor)
    {
        return toAjax(acwGreenhouseMonitorService.updateAcwGreenhouseMonitor(acwGreenhouseMonitor));
    }

    /**
     * 删除大棚监测
     */
    @Operation(summary = "删除大棚监测")
    @Parameter(name = "ids", description = "大棚监测ID数组")
    @ApiResponse(useReturnTypeSchema = false, description = "返回结果JSON格式", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "data", schema = @Schema(description = "返回数据", type = "Object"))
                    }
            )
    )
    @PreAuthorize("@ss.hasPermi('system:ghMonitor:remove')")
    @Log(title = "大棚监测", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(acwGreenhouseMonitorService.deleteAcwGreenhouseMonitorByIds(ids));
    }

    /**
     * 获取过去24小时环境监测报表
     * @param greenhouseId
     * @param start
     * @return
     */
    @Operation(summary = "获取过去24小时环境监测报表")
    @Parameters({
            @Parameter(name = "greenhouseId", description = "大棚ID"),
            @Parameter(name = "start", description = "开始时间，格式：yyyy-MM-dd HH:mm:ss")
    })
    @ApiResponse(useReturnTypeSchema = false, description = "返回结果JSON格式", responseCode = "200",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    schemaProperties = {
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "code", schema = @Schema(description = "状态码：200 成功", type = "int")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "msg", schema = @Schema(description = "异常时消息内容", type = "String")),
                            @io.swagger.v3.oas.annotations.media.SchemaProperty(name = "data", schema = @Schema(description = "返回数据", implementation = GreenHouseReportVO.class))
                    }
            )
    )
    @GetMapping("/report/hour")
    public AjaxResult report4Hour(Long greenhouseId, String start) {
        //获取过去24小时的数据
        Date endTime = new Date();
        if (StringUtils.isNotBlank(start)) {
            endTime = DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS, start);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endTime);
        calendar.add(Calendar.HOUR_OF_DAY, -24);
        Date startTime = calendar.getTime();

        GreenHouseReportVO report = new GreenHouseReportVO();

        //初始化时间
        for (int i = 0; i < 24; i++) {
            calendar.add(Calendar.HOUR_OF_DAY, 1);
            String time = DateUtils.parseDateToStr("MM-dd HH:00", calendar.getTime());
            report.getTimes().add(time);
        }

        if (greenhouseId == null) {
            return AjaxResult.success(report);
        }

        AcwGreenhouseMonitor filter = new AcwGreenhouseMonitor();
        filter.setGhId(greenhouseId);
        filter.getParams().put("beginDataTime", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, startTime));
        filter.getParams().put("endDataTime", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, endTime));
        List<AcwGreenhouseMonitor> acwPoolMonitors = acwGreenhouseMonitorService.selectAcwGreenhouseMonitorList(filter);

        float[] hourlyTemperatureSum = new float[24];
        float[] hourlyHumiSSum = new float[24];
        float[] hourlySmokeSum = new float[24];
        float[] hourlyPm25Sum = new float[24];

        int[] hourlyRecordCount = new int[24];

        for (AcwGreenhouseMonitor monitor : acwPoolMonitors) {
            //计算下标
            Calendar monitorCalendar = Calendar.getInstance();
            monitorCalendar.setTime(monitor.getDataTime());
            String time = DateUtils.parseDateToStr("MM-dd HH:00", monitorCalendar.getTime());
            int index = report.getTimes().indexOf(time);
            if (index == -1) {
                continue;
            }
            //计算总和
            hourlyTemperatureSum[index] += Float.parseFloat(monitor.getTemperature() != null ? monitor.getTemperature() : "0");
            hourlyHumiSSum[index] += Float.parseFloat(monitor.getHumility() != null ? monitor.getHumility() : "0");
            hourlySmokeSum[index] += Float.parseFloat(monitor.getSmoke() != null ? monitor.getSmoke() : "0");
            hourlyPm25Sum[index] += Float.parseFloat(monitor.getPm25() != null ? monitor.getPm25() : "0");
            hourlyRecordCount[index]++;
        }

        //计算平均值
        for (int i = 0; i < 24; i++) {
            double averageTemperature = hourlyRecordCount[i] > 0 ? NumberUtil.div(hourlyTemperatureSum[i], hourlyRecordCount[i], 1) : 0;
            double averageHumi = hourlyRecordCount[i] > 0 ? NumberUtil.div(hourlyHumiSSum[i], hourlyRecordCount[i], 1) : 0;
            double averageSmoke = hourlyRecordCount[i] > 0 ? NumberUtil.div(hourlySmokeSum[i], hourlyRecordCount[i], 1) : 0;
            double averagePm25 = hourlyRecordCount[i] > 0 ? NumberUtil.div(hourlyPm25Sum[i], hourlyRecordCount[i], 1) : 0;
            report.getTemperature().add(averageTemperature);
            report.getHumi().add(averageHumi);
            report.getSmoke().add(averageSmoke);
            report.getPm25().add(averagePm25);
        }

        return AjaxResult.success(report);
    }

    /**
     * 获取过去60分钟环境监测报表
     * @param greenhouseId
     * @param start
     * @return
     */
    @Operation(summary = "获取过去60分钟环境监测报表",deprecated = true)
    @GetMapping("/report/minute")
    public AjaxResult report4Minute(Long greenhouseId, String start) {
        //获取过去60分钟的数据
        Date endTime = new Date();
        if (StringUtils.isNotBlank(start)) {
            endTime = DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS, start);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endTime);
        calendar.add(Calendar.MINUTE, -60);
        Date startTime = calendar.getTime();

        AcwGreenhouseMonitor filter = new AcwGreenhouseMonitor();
        filter.setGhId(greenhouseId);
        filter.getParams().put("beginDataTime", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, startTime));
        filter.getParams().put("endDataTime", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, endTime));
        PageHelper.orderBy("data_time asc");
        List<AcwGreenhouseMonitor> vitalSigns = acwGreenhouseMonitorService.selectAcwGreenhouseMonitorList(filter);

        GreenHouseReportVO report = new GreenHouseReportVO();

        if(vitalSigns.isEmpty()){
            String time = DateUtils.parseDateToStr("HH:mm", calendar.getTime());
            report.getTimes().add(time);
            for(int i=0;i<12;i++){
                calendar.add(Calendar.MINUTE, 5);
                time = DateUtils.parseDateToStr("HH:mm", calendar.getTime());
                report.getTimes().add(time);
            }
            return AjaxResult.success(report);
        }

        //初始化时间
        for (AcwGreenhouseMonitor monitor : vitalSigns) {
            if(monitor.getDataTime()==null){
                continue;
            }
            //初始化时间
            Calendar monitorCalendar = Calendar.getInstance();
            monitorCalendar.setTime(monitor.getDataTime());
            String time = DateUtils.parseDateToStr("HH:mm", monitorCalendar.getTime());
            int index = report.getTimes().indexOf(time);
            if (index == -1) {
                report.getTimes().add(time);
            }
        }
        int size = report.getTimes().size();
        double[] temperatureArray = new double[size];
        double[] smokeArray = new double[size];
        double[] humiArray = new double[size];
        double[] pm25Array = new double[size];

        for (AcwGreenhouseMonitor monitor : vitalSigns) {
            if(monitor.getDataTime()==null){
                continue;
            }
            //计算下标
            Calendar monitorCalendar = Calendar.getInstance();
            monitorCalendar.setTime(monitor.getDataTime());
            String time = DateUtils.parseDateToStr("HH:mm", monitorCalendar.getTime());
            int index = report.getTimes().indexOf(time);
            if (index == -1) {
                continue;
            }
            //设置数据
            temperatureArray[index] = Float.parseFloat(monitor.getTemperature() != null ? monitor.getTemperature() : "0");
            smokeArray[index] = Float.parseFloat(monitor.getSmoke() != null ? monitor.getSmoke() : "0");
            humiArray[index] = Float.parseFloat(monitor.getHumility() != null ? monitor.getHumility() : "0");
            pm25Array[index] = Float.parseFloat(monitor.getPm25() != null ? monitor.getPm25() : "0");
        }

        //设置报表数据
        for (int i = 0; i < size; i++) {
            report.getTemperature().add(temperatureArray[i]);
            report.getSmoke().add(smokeArray[i]);
            report.getHumi().add(humiArray[i]);
            report.getPm25().add(pm25Array[i]);
        }

        return AjaxResult.success(report);
    }
}
