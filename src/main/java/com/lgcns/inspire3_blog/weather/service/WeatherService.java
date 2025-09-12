package com.lgcns.inspire3_blog.weather.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lgcns.inspire3_blog.weather.dto.WeatherRequestDTO;
import com.lgcns.inspire3_blog.weather.dto.WeatherResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WeatherService {

    @Value("${openApi.serviceKey}")
    private String serviceKey;

    @Value("${openApi.callBackUrl}")
    private String callBackUrl;

    @Value("${openApi.dataType}")
    private String dataType;

    public WeatherResponseDTO getWeatherInfo(WeatherRequestDTO request) {
        String url = callBackUrl
                    + "?serviceKey=" + serviceKey
                    + "&numOfRows=100&pageNo=1"
                    + "&dataType=" + dataType
                    + "&base_date=" + request.getBaseDate()
                    + "&base_time=" + request.getBaseTime()
                    + "&nx=60"
                    + "&ny=127";
        
        try {
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);
            JsonNode items = root.path("response").path("body").path("items").path("item");
            
            String temperature = null;
            String rainfall = null;
            String humidity = null;
            String windDirection = null;
            String windSpeed = null;
            String skyStatus = null;

            for (JsonNode item : items) {
                String category = item.get("category").asText();
                String value = item.get("fcstValue").asText();

                switch (category) {
                    case "TMP": temperature = value + "℃"; break;
                    case "PCP": rainfall = (value.equals("0") || value.equals("-")) ? "강수없음" : value; break;
                    case "REH": humidity = value + "%"; break;
                    case "VEC": windDirection = convertWindDirection(value); break;
                    case "WSD": windSpeed = value + "m/s"; break;
                    case "SKY": skyStatus = convertSky(value); break;
                }
            }

            return WeatherResponseDTO.builder()
                    .temperature(temperature)
                    .rainfall(rainfall)
                    .humidity(humidity)
                    .windDirection(windDirection)
                    .windSpeed(windSpeed)
                    .skyStatus(skyStatus)
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * 북위 코드를 한글로 매핑하는 메서드
     */
    private String convertWindDirection(String degreeStr) {
        try {
            double degree = Double.parseDouble(degreeStr);
            String[] directions = {"북", "북북동", "북동", "동북동", "동", "동남동", "남동", "남남동",
                                "남", "남남서", "남서", "서남서", "서", "서북서", "북서", "북북서"};
            int idx = (int) Math.round(((degree % 360) / 22.5));
            return directions[idx % 16];
        } catch (Exception e) {
            return degreeStr + "°";
        }
    }

    /*
     * 하늘 상태 코드를 한글로 매핑하는 메서드
     */
    private String convertSky(String code) {
        switch (code) {
            case "1": return "맑음";
            case "3": return "구름많음";
            case "4": return "흐림";
            default: return "알수없음";
        }
    }

}
