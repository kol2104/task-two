package com.epam.tasktwo.service;

import com.epam.tasktwo.mapper.WeatherMapper;
import com.epam.tasktwo.model.WeatherEntity;
import com.epam.tasktwo.model.WeatherInfo;
import com.epam.tasktwo.repository.WeatherRepository;
import com.epam.tasktwo.service.impl.WeatherServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherServiceImplTest {

    @Mock
    private WeatherRepository weatherRepository;

    @Mock
    private WeatherMapper weatherMapper;

    @InjectMocks
    private WeatherServiceImpl weatherService;

    @Test
    void testSave_WeatherEntityFromRepositoryNotNull() {
        // Prepare test data
        WeatherEntity weatherEntity = new WeatherEntity();
        weatherEntity.setCityName("London");

        WeatherEntity weatherEntityFromRepository = new WeatherEntity();
        weatherEntityFromRepository.setId(1L);

        // Mock repository behavior
        when(weatherRepository.findFirstByCityName("London")).thenReturn(weatherEntityFromRepository);

        // Call the service method
        weatherService.save(weatherEntity);

        // Verify that ID is set from the repository entity
        verify(weatherRepository, times(1)).save(weatherEntity);
    }

    @Test
    void testSave_WeatherEntityFromRepositoryNull() {
        // Prepare test data
        WeatherEntity weatherEntity = new WeatherEntity();
        weatherEntity.setCityName("London");

        // Mock repository behavior
        when(weatherRepository.findFirstByCityName("London")).thenReturn(null);

        // Call the service method
        weatherService.save(weatherEntity);

        // Verify that save is called with the provided entity
        verify(weatherRepository, times(1)).save(weatherEntity);
    }

    @Test
    void testGetWeatherInfoByCity() {
        // Prepare test data
        WeatherEntity weatherEntity = new WeatherEntity();
        weatherEntity.setCityName("London");

        WeatherInfo weatherInfo = new WeatherInfo();
        weatherInfo.setCityName("London");

        // Mock repository behavior
        when(weatherRepository.findFirstByCityName("London")).thenReturn(weatherEntity);
        when(weatherMapper.entityToInfo(weatherEntity)).thenReturn(weatherInfo);

        // Call the service method
        WeatherInfo result = weatherService.getWeatherInfoByCity("London");

        // Verify the result
        assertEquals("London", result.getCityName());
    }

    @Test
    void testGetWeatherInfoByZip() {
        // Prepare test data
        WeatherEntity weatherEntity = new WeatherEntity();
        weatherEntity.setZipCode("10001");

        WeatherInfo weatherInfo = new WeatherInfo();
        weatherInfo.setZipCode("10001");

        // Mock repository behavior
        when(weatherRepository.findFirstByZipCode("10001")).thenReturn(weatherEntity);
        when(weatherMapper.entityToInfo(weatherEntity)).thenReturn(weatherInfo);

        // Call the service method
        WeatherInfo result = weatherService.getWeatherInfoByZip("10001");

        // Verify the result
        assertEquals("10001", result.getZipCode());
    }
}