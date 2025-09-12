package com.lgcns.inspire3_blog.weather.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lgcns.inspire3_blog.weather.dto.WeatherRequestDTO;
import com.lgcns.inspire3_blog.weather.dto.WeatherResponseDTO;
import com.lgcns.inspire3_blog.weather.service.WeatherService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/v1/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @PostMapping("/short-term/info")
    public ResponseEntity<WeatherResponseDTO> getWeatherInfo(@RequestBody WeatherRequestDTO request) {
        WeatherResponseDTO weatherInfo = weatherService.getWeatherInfo(request);
        return ResponseEntity.ok(weatherInfo);
    }
    
}
