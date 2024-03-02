package com.epam.tasktwo.mapper;

import com.epam.tasktwo.model.WeatherEntity;
import com.epam.tasktwo.model.WeatherInfo;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface WeatherMapper {
    WeatherEntity infoToEntity(WeatherInfo weatherInfo);
    WeatherInfo entityToInfo(WeatherEntity weatherEntity);
}
