package com.epam.tasktwo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "weatherapi")
@Getter
@Setter
public class WeatherProperties {
    private List<String> cityNames;
    private List<String> zipCodes;
}