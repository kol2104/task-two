package com.epam.tasktwo.schedule;

import com.epam.tasktwo.config.WeatherProperties;
import com.epam.tasktwo.mapper.WeatherMapper;
import com.epam.tasktwo.model.WeatherEntity;
import com.epam.tasktwo.model.WeatherInfo;
import com.epam.tasktwo.service.RemoteWeatherServiceApi;
import com.epam.tasktwo.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherUpdateServiceTest {

    @Mock
    private WeatherService weatherService;

    @Mock
    private WeatherMapper weatherMapper;

    @Mock
    private WeatherProperties weatherProperties;

    @Mock
    private RemoteWeatherServiceApi remoteWeatherServiceApi;

    @InjectMocks
    private WeatherUpdateService weatherUpdateService;

    @Test
    void testUpdateWeatherData_WeatherEntityNotNull() {
        // Prepare test data
        WeatherInfo weatherInfo = new WeatherInfo();
        weatherInfo.setCityName("London");

        WeatherEntity weatherEntity = new WeatherEntity();
        weatherEntity.setCityName("London");

        // Mock WeatherProperties
        List<String> cityNames = List.of("London");
        WeatherProperties properties = new WeatherProperties();
        properties.setCityNames(cityNames);
        properties.setZipCodes(List.of());
        when(weatherProperties.getCityNames()).thenReturn(properties.getCityNames());
        when(weatherProperties.getZipCodes()).thenReturn(properties.getZipCodes());

        // Mock remote service response
        when(remoteWeatherServiceApi.getWeatherInfo("London")).thenReturn(weatherInfo);

        // Mock mapper behavior
        when(weatherMapper.infoToEntity(weatherInfo)).thenReturn(weatherEntity);

        // Call the service method
        weatherUpdateService.updateWeatherData();

        // Verify that save method is called with the entity
        verify(weatherService, times(1)).save(weatherEntity);
    }

    @Test
    void testUpdateWeatherData_WeatherEntityNull() {
        // Prepare test data
        WeatherInfo weatherInfo = new WeatherInfo();
        weatherInfo.setCityName("London");

        // Mock WeatherProperties
        List<String> cityNames = List.of("London");
        WeatherProperties properties = new WeatherProperties();
        properties.setCityNames(cityNames);
        properties.setZipCodes(List.of());
        when(weatherProperties.getCityNames()).thenReturn(properties.getCityNames());
        when(weatherProperties.getZipCodes()).thenReturn(properties.getZipCodes());

        // Mock remote service response
        when(remoteWeatherServiceApi.getWeatherInfo("London")).thenReturn(null);

        // Call the service method
        weatherUpdateService.updateWeatherData();

        // Verify that save method is not called
        verify(weatherService, never()).save(any(WeatherEntity.class));
    }

    @Test
    void testUpdateWeatherData_WeatherEntityFromZipCodeNotNull() {
        // Prepare test data
        WeatherInfo weatherInfo = new WeatherInfo();
        weatherInfo.setZipCode("10001");

        WeatherEntity weatherEntity = new WeatherEntity();
        weatherEntity.setZipCode("10001");

        // Mock WeatherProperties
        List<String> zipCodes = List.of("10001");
        WeatherProperties properties = new WeatherProperties();
        properties.setCityNames(List.of());
        properties.setZipCodes(zipCodes);
        when(weatherProperties.getCityNames()).thenReturn(properties.getCityNames());
        when(weatherProperties.getZipCodes()).thenReturn(properties.getZipCodes());

        // Mock remote service response
        when(remoteWeatherServiceApi.getWeatherInfo("10001")).thenReturn(weatherInfo);

        // Mock mapper behavior
        when(weatherMapper.infoToEntity(weatherInfo)).thenReturn(weatherEntity);

        // Call the service method
        weatherUpdateService.updateWeatherData();

        // Verify that save method is called with the entity
        verify(weatherService, times(1)).save(weatherEntity);
    }

    @Test
    void testUpdateWeatherData_WeatherEntityFromZipCodeNull() {
        // Prepare test data
        WeatherInfo weatherInfo = new WeatherInfo();
        weatherInfo.setZipCode("10001");

        // Mock WeatherProperties
        List<String> zipCodes = List.of("10001");
        WeatherProperties properties = new WeatherProperties();
        properties.setCityNames(List.of());
        properties.setZipCodes(zipCodes);
        when(weatherProperties.getCityNames()).thenReturn(properties.getCityNames());
        when(weatherProperties.getZipCodes()).thenReturn(properties.getZipCodes());

        // Mock remote service response
        when(remoteWeatherServiceApi.getWeatherInfo("10001")).thenReturn(null);

        // Call the service method
        weatherUpdateService.updateWeatherData();

        // Verify that save method is not called
        verify(weatherService, never()).save(any(WeatherEntity.class));
    }
}