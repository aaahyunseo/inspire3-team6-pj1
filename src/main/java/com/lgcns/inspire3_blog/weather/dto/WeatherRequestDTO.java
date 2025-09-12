package com.lgcns.inspire3_blog.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherRequestDTO {
    // YYYYMMDD
    private String baseDate;
    // HHMM
    private String baseTime;
}
