package com.epam.tasktwo.mapper;

import com.epam.tasktwo.model.WeatherEntity;
import com.epam.tasktwo.model.WeatherInfo;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WeatherMapperTest {

    private final WeatherMapper weatherMapper = Mappers.getMapper(WeatherMapper.class);

    @Test
    void testInfoToEntity() {
        // Given
        WeatherInfo weatherInfo = new WeatherInfo();
        weatherInfo.setCityName("London");
        weatherInfo.setCountry("UK");

        // When
        WeatherEntity weatherEntity = weatherMapper.infoToEntity(weatherInfo);

        // Then
        assertNotNull(weatherEntity);
        assertEquals("London", weatherEntity.getCityName());
        assertEquals("UK", weatherEntity.getCountry());
    }

    @Test
    void testEntityToInfo() {
        // Given
        WeatherEntity weatherEntity = new WeatherEntity();
        weatherEntity.setCityName("London");
        weatherEntity.setCountry("UK");

        // When
        WeatherInfo weatherInfo = weatherMapper.entityToInfo(weatherEntity);

        // Then
        assertNotNull(weatherInfo);
        assertEquals("London", weatherInfo.getCityName());
        assertEquals("UK", weatherInfo.getCountry());
    }
}
