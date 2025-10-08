package com.neu.system.ai.data;

import lombok.Data;

@Data
public class WeatherData {
    private String status;
    private String info;
    private String infocode;
    private LiveWeather[] lives;

    @Data
    public static class LiveWeather {
        private double temperature;
        private double humidity;
        private String windpower;
        private String winddirection;
        private String weather;
        private String city;

        public String formatWeather() {
            return String.format("温度%s°C，湿度%s%%，风力%s级%s风，天气现象：%s",
                    temperature,
                    humidity,
                    windpower,
                    winddirection,
                    weather);
        }
    }
}
