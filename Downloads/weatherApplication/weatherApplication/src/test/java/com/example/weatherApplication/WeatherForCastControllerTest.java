package com.example.weatherApplication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.weatherApplication.Exception.WeatherForecastException;
import com.example.weatherApplication.weatherforcastcontroller.WeatherForCastController;
import com.example.weatherApplication.weatherforcastservice.WeatherForCastService;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class WeatherForCastControllerTest {

	private WeatherForCastController weatherController;
    private WeatherForCastService weatherService;
    
    @BeforeEach
    void setUp() {
        weatherService = mock(WeatherForCastService.class);
        weatherController = new WeatherForCastController();
    }
    
    @Test
    void testGetWeatherForecast_Success() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request, new MockHttpServletResponse()));
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.addHeader("Content-Type", "application/json");

        weatherService = mock(WeatherForCastService.class);
        weatherController = new WeatherForCastController();

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(weatherController).build();

        String city = "London";
        String weatherData = "{\"temperature\": 25, \"description\": \"Sunny\"}";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(weatherData, HttpStatus.OK);

        when(weatherService.getWeatherForecast(anyString())).thenReturn(responseEntity);

        ResponseEntity<String> result = weatherController.getWeatherForecast(anyString());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(weatherData, result.getBody());
    }

    @Test
    void testGetWeatherForecast_ServiceError() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request, new MockHttpServletResponse()));

        String city = "InvalidCity";

        when(weatherService.getWeatherForecast(Mockito.anyString())).thenThrow(new WeatherForecastException("Service error"));

        ResponseEntity<String> result = weatherController.getWeatherForecast(city);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        assertTrue(result.getBody().contains("Service error"));
    }
    
	
}
