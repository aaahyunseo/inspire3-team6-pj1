package com.lgcns.inspire3_blog.weather.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lgcns.inspire3_blog.weather.dto.WeatherResponseDTO;
import com.lgcns.inspire3_blog.weather.service.WeatherService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/v1/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/short-term/info")
    public ResponseEntity<WeatherResponseDTO> getWeatherInfo() {
        WeatherResponseDTO weatherInfo = weatherService.getWeatherInfo();
        return ResponseEntity.ok(weatherInfo);
    }
    
}
