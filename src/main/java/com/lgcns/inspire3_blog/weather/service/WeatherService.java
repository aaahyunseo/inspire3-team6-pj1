package com.lgcns.inspire3_blog.weather.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lgcns.inspire3_blog.weather.dto.WeatherRequestDTO;
import com.lgcns.inspire3_blog.weather.dto.WeatherResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherService {

    @Value("${openApi.serviceKey}")
    private String serviceKey;

    @Value("${openApi.callBackUrl}")
    private String callBackUrl;

    @Value("${openApi.dataType}")
    private String dataType;

    private static final String[] VALID_BASE_TIMES = {
        "0200", "0500", "0800", "1100", "1400", "1700", "2000", "2300"
    };

    public WeatherResponseDTO getWeatherInfo(WeatherRequestDTO request) {
        LocalDateTime now = LocalDateTime.of(
            LocalDate.parse(request.getBaseDate(), DateTimeFormatter.BASIC_ISO_DATE),
            LocalTime.parse(request.getBaseTime(), DateTimeFormatter.ofPattern("HHmm"))
        );

        String adjustedBaseTime = adjustBaseTime(now);
        String adjustedBaseDate = request.getBaseDate();

        if (adjustedBaseTime.equals("2300") && now.getHour() < 2) {
            adjustedBaseDate = now.minusDays(1).format(DateTimeFormatter.BASIC_ISO_DATE);
        }

        String url = callBackUrl
                + "?serviceKey=" + serviceKey
                + "&numOfRows=100&pageNo=1"
                + "&dataType=" + dataType
                + "&base_date=" + adjustedBaseDate
                + "&base_time=" + adjustedBaseTime
                + "&nx=60"
                + "&ny=127";

        log.info(">>> base_date={}, base_time={}", adjustedBaseDate, adjustedBaseTime);
    
        
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

    /**
     * 주어진 현재 시간(hhmm)과 가장 가까운 base_time 반환
     * ex) 15:37 -> 1400, 18:05 -> 1700
     */
    private String adjustBaseTime(LocalDateTime now) {
        int hhmm = now.getHour() * 100 + now.getMinute();

        // 기본값: 가장 최근 시각
        String selected = "0200";

        for (String base : VALID_BASE_TIMES) {
            int baseInt = Integer.parseInt(base);
            if (hhmm >= baseInt) {
                selected = base;
            }
        }

        // 자정 ~ 새벽 2시 전까지는 전날 23시 예보 사용
        if (hhmm < 200) {
            selected = "2300";
        }

        return selected;
    }
}
