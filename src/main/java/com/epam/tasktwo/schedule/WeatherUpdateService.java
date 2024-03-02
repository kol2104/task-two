package com.epam.tasktwo.schedule;

import com.epam.tasktwo.config.WeatherProperties;
import com.epam.tasktwo.mapper.WeatherMapper;
import com.epam.tasktwo.model.WeatherEntity;
import com.epam.tasktwo.model.WeatherInfo;
import com.epam.tasktwo.service.RemoteWeatherServiceApi;
import com.epam.tasktwo.service.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class WeatherUpdateService {

    private final WeatherService weatherService;

    private final WeatherMapper weatherMapper;

    private final RemoteWeatherServiceApi remoteWeatherServiceApi;

    private final WeatherProperties weatherProperties;

    @Scheduled(fixedRate = 3600000) // Run every hour (in milliseconds)
    public void updateWeatherData() {
        List<String> listOfCities = new ArrayList<>(weatherProperties.getCityNames());
        for (String location : listOfCities) {
            WeatherInfo weatherInfo = remoteWeatherServiceApi.getWeatherInfo(location);

            WeatherEntity weatherEntity = weatherMapper.infoToEntity(weatherInfo);

            if (weatherEntity != null) {
                weatherService.save(weatherEntity);
            }
        }
        List<String> listOfCodes = new ArrayList<>(weatherProperties.getZipCodes());
        for (String location : listOfCodes) {
            WeatherInfo weatherInfo = remoteWeatherServiceApi.getWeatherInfo(location);

            WeatherEntity weatherEntity = weatherMapper.infoToEntity(weatherInfo);

            if (weatherEntity != null) {
                weatherEntity.setZipCode(location);
                weatherService.save(weatherEntity);
            }
        }
    }
}