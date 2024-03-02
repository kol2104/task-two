package com.epam.tasktwo.service;

import com.epam.tasktwo.model.WeatherInfo;

public interface RemoteWeatherServiceApi {
    WeatherInfo getWeatherInfo(String cityName);
}
