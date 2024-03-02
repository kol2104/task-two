package com.epam.tasktwo.controller;

import com.epam.tasktwo.model.WeatherInfo;
import com.epam.tasktwo.service.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/weather/by-city")
    public ResponseEntity<WeatherInfo> getWeatherByCity(@RequestParam String cityName) {
        WeatherInfo weatherInfo = weatherService.getWeatherInfoByCity(cityName);
        if (weatherInfo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(weatherInfo);
    }

    @GetMapping("/weather/by-zip")
    public ResponseEntity<WeatherInfo> getWeatherByZip(@RequestParam String zipCode) {
        WeatherInfo weatherInfo = weatherService.getWeatherInfoByCity(zipCode);
        if (weatherInfo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(weatherInfo);
    }
}