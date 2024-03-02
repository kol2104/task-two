package com.epam.tasktwo.service.impl;

import com.epam.tasktwo.model.WeatherInfo;
import com.epam.tasktwo.service.RemoteWeatherServiceApi;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
@Slf4j
public class RemoteWeatherServiceApiHttpClient implements RemoteWeatherServiceApi {

    private static final String URL_TEMPLATE = "%s/current.json?q=%s&key=%s";

    @Value("${weatherapi.base-url}")
    private String baseUrl;

    @Value("${weatherapi.api-key}")
    private String apiKey;

    private final ObjectMapper objectMapper;

    public RemoteWeatherServiceApiHttpClient(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public WeatherInfo getWeatherInfo(String location) {

        String url = String.format(URL_TEMPLATE, baseUrl, URLEncoder.encode(location, StandardCharsets.UTF_8), apiKey);

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);

        try {
            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() == 200) {
                String responseBody = EntityUtils.toString(response.getEntity());
                return mapResponseToWeatherInfo(responseBody);
            }
        } catch (Exception exception) {
            log.error("Error: {}", exception.getMessage());
        }
        return null;
    }

    private WeatherInfo mapResponseToWeatherInfo(String responseBody) throws JsonProcessingException {
        WeatherInfo weatherInfo = new WeatherInfo();

        Map<String, Object> jsonMap = objectMapper.readValue(responseBody, new TypeReference<>() {
        });

        // Extract necessary fields from the JSON response
        Map<String, Object> location = (Map<String, Object>) jsonMap.get("location");
        Map<String, Object> current = (Map<String, Object>) jsonMap.get("current");


        weatherInfo.setCityName((String) location.get("name"));
        weatherInfo.setCountry((String) location.get("country"));
        weatherInfo.setCelsiusTemperature(Double.valueOf(current.get("temp_c").toString()));
        weatherInfo.setFahrenheitTemperature(Double.valueOf(current.get("temp_f").toString()));
        weatherInfo.setCelsiusFeelsLike(Double.valueOf(current.get("feelslike_c").toString()));
        weatherInfo.setFahrenheitFeelsLike(Double.valueOf(current.get("feelslike_f").toString()));

        return weatherInfo;

    }
}
