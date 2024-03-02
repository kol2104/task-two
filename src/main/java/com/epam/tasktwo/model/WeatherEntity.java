package com.epam.tasktwo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
public class WeatherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String cityName;
    private String country;
    @Column(unique = true)
    private String zipCode;
    private Double celsiusTemperature;
    private Double fahrenheitTemperature;
    private Double celsiusFeelsLike;
    private Double fahrenheitFeelsLike;
}
