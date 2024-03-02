package com.epam.tasktwo.service;

import com.epam.tasktwo.model.WeatherInfo;
import com.epam.tasktwo.service.impl.RemoteWeatherServiceApiHttpClient;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RemoteWeatherServiceApiHttpClientTest {

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private RemoteWeatherServiceApiHttpClient remoteWeatherServiceApi;

    private static MockedStatic<HttpClientBuilder> mockedStatic;

    @BeforeAll
    public static void init() {
        mockedStatic = mockStatic(HttpClientBuilder.class);
    }

    @AfterAll
    public static void close() {
        mockedStatic.close();
    }

    @Test
    void testGetWeatherInfo_Success() throws Exception {
        // Mock response
        String responseBody = "{ \"location\": { \"name\": \"London\", \"country\": \"UK\" }, \"current\": { \"temp_c\": 10.0, \"temp_f\": 50.0, \"feelslike_c\": 9.5, \"feelslike_f\": 49.2 } }";
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("location", Map.of("name", "London", "country", "UK"));
        jsonMap.put("current", Map.of("temp_c", 10.0, "temp_f", 50.0, "feelslike_c", 9.5, "feelslike_f", 49.2));
        when(objectMapper.readValue(eq(responseBody), any(TypeReference.class))).thenReturn(jsonMap);


        // Mock HTTP client response
        CloseableHttpClient httpClient = mock(CloseableHttpClient.class);
        CloseableHttpResponse httpResponse = mock(CloseableHttpResponse.class);
        HttpClientBuilder httpClientBuilder = mock(HttpClientBuilder.class);
        HttpEntity httpEntity = mock(HttpEntity.class);
        StatusLine statusLine = mock(StatusLine.class);
        mockedStatic.when(HttpClientBuilder::create).thenReturn(httpClientBuilder);
        when(httpClientBuilder.build()).thenReturn(httpClient);
        when(httpClient.execute(any(HttpGet.class))).thenReturn(httpResponse);
        when(httpResponse.getStatusLine()).thenReturn(statusLine);
        when(statusLine.getStatusCode()).thenReturn(200);
        when(httpResponse.getEntity()).thenReturn(httpEntity);
        when(httpEntity.getContent()).thenReturn(new ByteArrayInputStream(responseBody.getBytes()));

        // Call the service method
        WeatherInfo weatherInfo = remoteWeatherServiceApi.getWeatherInfo("London");

        // Verify the result
        assertNotNull(weatherInfo);
        assertEquals("London", weatherInfo.getCityName());
        assertEquals("UK", weatherInfo.getCountry());
        assertEquals(10.0, weatherInfo.getCelsiusTemperature());
        assertEquals(50.0, weatherInfo.getFahrenheitTemperature());
        assertEquals(9.5, weatherInfo.getCelsiusFeelsLike());
        assertEquals(49.2, weatherInfo.getFahrenheitFeelsLike());
    }

    @Test
    void testGetWeatherInfo_Failure() throws Exception {
        // Mock HTTP client response
        CloseableHttpClient httpClient = mock(CloseableHttpClient.class);
        CloseableHttpResponse httpResponse = mock(CloseableHttpResponse.class);
        HttpClientBuilder httpClientBuilder = mock(HttpClientBuilder.class);
        StatusLine statusLine = mock(StatusLine.class);
        mockedStatic.when(HttpClientBuilder::create).thenReturn(httpClientBuilder);
        when(httpClientBuilder.build()).thenReturn(httpClient);
        when(httpClient.execute(any(HttpGet.class))).thenReturn(httpResponse);
        when(httpResponse.getStatusLine()).thenReturn(statusLine);
        when(statusLine.getStatusCode()).thenReturn(500);

        // Call the service method
        WeatherInfo weatherInfo = remoteWeatherServiceApi.getWeatherInfo("London");

        // Verify the result
        assertNull(weatherInfo);
    }

    @Test
    void testGetWeatherInfo_Exception() throws Exception {
        // Mock HTTP client response
        CloseableHttpClient httpClient = mock(CloseableHttpClient.class);
        HttpClientBuilder httpClientBuilder = mock(HttpClientBuilder.class);
        mockedStatic.when(HttpClientBuilder::create).thenReturn(httpClientBuilder);
        when(httpClientBuilder.build()).thenReturn(httpClient);
        when(httpClient.execute(any(HttpGet.class))).thenThrow(new RuntimeException("Connection refused"));

        // Call the service method
        WeatherInfo weatherInfo = remoteWeatherServiceApi.getWeatherInfo("London");

        // Verify the result
        assertNull(weatherInfo);
    }
}