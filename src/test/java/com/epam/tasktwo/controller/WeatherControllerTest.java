package com.epam.tasktwo.controller;

import com.epam.tasktwo.model.WeatherInfo;
import com.epam.tasktwo.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WeatherController.class)
class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherService weatherService;

    @Test
    void testGetWeatherByCity_WeatherInfoNotNull() throws Exception {
        // Mock service response
        WeatherInfo weatherInfo = new WeatherInfo();
        weatherInfo.setCityName("London");
        when(weatherService.getWeatherInfoByCity("London")).thenReturn(weatherInfo);

        // Perform GET request and verify response
        mockMvc.perform(MockMvcRequestBuilders.get("/weather/by-city")
                        .param("cityName", "London")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cityName").value("London"));
    }

    @Test
    void testGetWeatherByZip_WeatherInfoNotNull() throws Exception {
        // Mock service response
        WeatherInfo weatherInfo = new WeatherInfo();
        weatherInfo.setCityName("10001");
        when(weatherService.getWeatherInfoByCity("10001")).thenReturn(weatherInfo);

        // Perform GET request and verify response
        mockMvc.perform(MockMvcRequestBuilders.get("/weather/by-zip")
                        .param("zipCode", "10001")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cityName").value("10001"));
    }

    @Test
    void testGetWeatherByCity_WeatherInfoNull() throws Exception {
        // Mock service response
        when(weatherService.getWeatherInfoByCity("London")).thenReturn(null);

        // Perform GET request and verify response
        mockMvc.perform(MockMvcRequestBuilders.get("/weather/by-city")
                        .param("cityName", "London")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetWeatherByZip_WeatherInfoNull() throws Exception {
        // Mock service response
        when(weatherService.getWeatherInfoByCity("10001")).thenReturn(null);

        // Perform GET request and verify response
        mockMvc.perform(MockMvcRequestBuilders.get("/weather/by-zip")
                        .param("zipCode", "10001")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
