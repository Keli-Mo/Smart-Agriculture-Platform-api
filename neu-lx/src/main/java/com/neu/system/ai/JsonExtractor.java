package com.neu.system.ai;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonExtractor {
    public static void main(String[] args) {
        String input = "# 字符串\n" +
                "<think>\n" +
                "嗯，好的，我现在需要帮用户生成一个温室大棚的排风计划表。用户给了天气情况、种植作物以及其他相关参数，我得仔细分析这些信息。\n" +
                "\n" +
                "首先看天气情况：温度20.0°C，湿度51%，风力4级南风，多云。这个环境比较稳定，湿度不算很高，适合排风操作。风力适中，可能有助于促进气体交换。\n" +
                "\n" +
                "种植作物是草莓和樱桃，它们的生长阶段不同。草莓通常在3-6月生长，而樱桃则在5-7月。所以排风计划可能需要分别考虑，但用户要求的是一个综合的计划表，可能需要合并处理。\n" +
                "\n" +
                "用户希望按照指定JSON格式输出，并且要包含温度调控、湿度管理、风力利用和应对天气现象等因素。我得确保每个方面都被考虑到。\n" +
                "\n" +
                "排风的目的是为了维持适当的环境条件，促进作物生长。草莓可能需要较高的湿度和较低的风速，而樱桃则可能在高湿时更需要通风，防止腐烂。不过这里用户已经给出了一个排风计划表，可能已经考虑了这些因素。\n" +
                "\n" +
                "根据天气情况，温度是20度，相对稳定，不需要过高的降温或升温操作。湿度51%接近饱和，排风可以帮助降低湿度，尤其是在樱桃阶段，这很重要。\n" +
                "\n" +
                "风力4级南风，虽然不大，但排风时可以利用风向来促进大棚内部的空气循环，保持湿度均匀。\n" +
                "\n" +
                "天气现象是多云，不会有雨雪，不需要额外的排水措施，同时也能保证排风顺畅。所以可以在排风计划表中安排适当的时间段，比如早晨和傍晚，这两个时间段排风有助于提高湿度均匀性，并且减少昼夜温差。\n" +
                "\n" +
                "排风时长每天60分钟，这个时间刚好足够进行一次全面排风操作，同时不会导致作物受到太大影响。频率为每天2次，这样可以覆盖白天和黑夜的排风需求，保持环境稳定。\n" +
                "\n" +
                "最后，我需要将这些信息整合成JSON格式，确保每个时间段准确无误，并且按照用户的要求不添加额外说明。\n" +
                "</think>\n" +
                "```json\n" +
                "[\n" +
                "  {\n" +
                "    \"startTime\": \"05:00\",  // 排风开始时间\n" +
                "    \"duration\": 60,          // 实际排风时长(分钟)\n" +
                "  },\n" +
                "  {\n" +
                "    \"startTime\": \"17:00\",\n" +
                "    \"duration\": 60,\n" +
                "  }\n" +
                "]\n" +
                "```";

        String json = extractJson(input);
        System.out.println(json);
    }

    public static String extractJson(String input) {
        // 定义正则表达式来匹配 JSON 块
        Pattern pattern = Pattern.compile("```json(.*?)```", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            // 提取匹配到的 JSON 数据并去除前后空格
            return matcher.group(1).trim();
        }
        return "";
    }
}
