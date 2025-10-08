package com.neu.system.vo;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 报表数据VO
 */
@Schema(description = "报表数据VO",title = "com.neu.system.vo.GreenHouseReportVO")
@Getter
@Setter
public class GreenHouseReportVO {
    //每个小时的时间，格式：['2月1日 12:00', '2月1日 13:00', '2月1日 14:00', '2月1日 15:00', '2月2日 16:00', '2月2日 17:00']
    @ArraySchema(arraySchema = @Schema(description = "每个小时的时间", example = "['2月1日 12:00', '2月1日 13:00', '2月1日 14:00', '2月1日 15:00', '2月2日 16:00', '2月2日 17:00']"))
    private List<String> times;
    //每个小时的平均温度，格式：[23.5, 24.0, 24.5, 25.0, 25.5, 26.0]
    @ArraySchema(arraySchema = @Schema(description = "每个小时的平均温度", example = "[23.5, 24.0, 24.5, 25.0, 25.5, 26.0]"))
    private List<Double> temperature;
    //每个小时的平均湿度，格式：[50, 55, 60, 65, 70, 75]
    @ArraySchema(arraySchema = @Schema(description = "每个小时的平均湿度", example = "[50, 55, 60, 65, 70, 75]"))
    private List<Double> humi;
    //每个小时的平均烟雾值，格式：[50, 55, 60, 65, 70, 75]
    @ArraySchema(arraySchema = @Schema(description = "每个小时的平均烟雾值", example = "[50, 55, 60, 65, 70, 75]"))
    private List<Double> smoke;
    //每个小时的平均PM2.5，格式：[50, 55, 60, 65, 70, 75]
    @ArraySchema(arraySchema = @Schema(description = "每个小时的平均PM2.5", example = "[50, 55, 60, 65, 70, 75]"))
    private List<Double> pm25;

    public GreenHouseReportVO() {
        this.times = new ArrayList<>();
        this.temperature = new ArrayList<>();
        this.humi = new ArrayList<>();
        this.smoke = new ArrayList<>();
        this.pm25 = new ArrayList<>();
    }
}
