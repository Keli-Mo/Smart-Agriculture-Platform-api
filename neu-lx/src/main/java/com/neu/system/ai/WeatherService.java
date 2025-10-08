package com.neu.system.ai;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neu.system.ai.data.RegeoData;
import com.neu.system.ai.data.WeatherData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

/**
 * 天气服务
 */
@Service
@Slf4j
public class WeatherService {
    public static final String WAETHER_URL = "https://restapi.amap.com/v3/weather/weatherInfo?city=%s&key=%s";
    public static final String CITY_CODE_URL = "https://restapi.amap.com/v3/geocode/regeo?location=%.6f,%.6f&key=%s";
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${gaode.key}")
    private String gaodeApiKey;

    private RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        this.restTemplate = restTemplateBuilder.build();
    }

    public WeatherData.LiveWeather getLiveWeather(String cityCode) {
        String url = String.format(WAETHER_URL, cityCode, gaodeApiKey);
        log.info("高德API调用URL: {}", url);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("高德API响应: {}", response.getBody());
            WeatherData result = null;
            try {
                result = objectMapper.readValue(response.getBody(), WeatherData.class);
            } catch (JsonProcessingException e) {
                log.warn("解析高德API响应失败", e);
                WeatherData.LiveWeather mock = new WeatherData.LiveWeather();
                mock.setWeather("晴");
                mock.setTemperature(25);
                mock.setWinddirection("东北风");
                mock.setWindpower("3");
                mock.setHumidity(50);
                return mock;
            }
            if ("1".equals(result.getStatus())) {
                return result.getLives()[0];
            } else {
                throw new RuntimeException("高德API调用失败: " + result.getInfo());
            }
        } else {
            throw new RuntimeException("HTTP请求失败: " + response.getStatusCode());
        }
    }

    public RegeoData getRegeoData(double longitude, double latitude) throws Exception {
        String url = String.format(CITY_CODE_URL, longitude, latitude, gaodeApiKey);
        log.info("高德API调用URL: {}", url);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("高德API响应: {}", response.getBody());
            RegeoData result = objectMapper.readValue(response.getBody(), RegeoData.class);
            if ("1".equals(result.getStatus())) {
                return result;
            } else {
                throw new RuntimeException("高德API调用失败: " + result);
            }
        } else {
            throw new RuntimeException("HTTP请求失败: " + response.getStatusCode());
        }
    }
}
