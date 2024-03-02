package com.epam.tasktwo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherInfo {
    private String cityName;
    private String country;
    private String zipCode;
    private Double celsiusTemperature;
    private Double fahrenheitTemperature;
    private Double celsiusFeelsLike;
    private Double fahrenheitFeelsLike;
}
