package com.epam.tasktwo.service.impl;

import com.epam.tasktwo.mapper.WeatherMapper;
import com.epam.tasktwo.model.WeatherEntity;
import com.epam.tasktwo.model.WeatherInfo;
import com.epam.tasktwo.repository.WeatherRepository;
import com.epam.tasktwo.service.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class WeatherServiceImpl implements WeatherService {

    private final WeatherRepository weatherRepository;
    private final WeatherMapper weatherMapper;

    @Override
    public void save(WeatherEntity weatherEntity) {
        WeatherEntity weatherEntityFromRepository =
                weatherRepository.findFirstByCityName(weatherEntity.getCityName());
        if (weatherEntityFromRepository != null) {
            weatherEntity.setId(weatherEntityFromRepository.getId());
        }
        weatherRepository.save(weatherEntity);
    }

    @Override
    public WeatherInfo getWeatherInfoByCity(String cityName) {
        return weatherMapper.entityToInfo(weatherRepository.findFirstByCityName(cityName));
    }

    @Override
    public WeatherInfo getWeatherInfoByZip(String zipCode) {
        return weatherMapper.entityToInfo(weatherRepository.findFirstByZipCode(zipCode));
    }
}
