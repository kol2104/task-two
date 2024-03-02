package com.epam.tasktwo.service;

import com.epam.tasktwo.model.WeatherEntity;
import com.epam.tasktwo.model.WeatherInfo;

public interface WeatherService {

    void save(WeatherEntity weatherEntity);
    WeatherInfo getWeatherInfoByCity(String cityName);
    WeatherInfo getWeatherInfoByZip(String zipCode);
}
