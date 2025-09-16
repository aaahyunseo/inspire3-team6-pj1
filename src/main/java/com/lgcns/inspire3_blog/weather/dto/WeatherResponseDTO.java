package com.lgcns.inspire3_blog.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherResponseDTO {
    // 기온
    private String temperature;
    // 강수량
    private String rainfall;
    // 습도
    private String humidity;
    // 풍향
    private String windDirection;
    // 풍속
    private String windSpeed;
    // 하늘 상태(맑음, 구름많음, 흐림)
    private String skyStatus;
}
