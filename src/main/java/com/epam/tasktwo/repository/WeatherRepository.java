package com.epam.tasktwo.repository;

import com.epam.tasktwo.model.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository extends JpaRepository<WeatherEntity, Long> {

    WeatherEntity findFirstByCityName(String cityName);

    WeatherEntity findFirstByZipCode(String zipCode);
}
