
package com.example.weatherApplication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.weatherApplication.Exception.WeatherForecastException;
import com.example.weatherApplication.weatherforcastservice.WeatherForCastService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WeatherForCastServiceTest {

	@InjectMocks
	private WeatherForCastService weatherService;
	@InjectMocks
	private RestTemplate restTemplate;

	@BeforeEach
	void setUp() {
		 restTemplate = mock(RestTemplate.class);
	     weatherService = new WeatherForCastService();
	}

	@Test
	void testGetWeatherForecast_Success() {
		String city = "London";
		String apiKey = "test-api-key";
		String baseUrl = "http://test-api.com";
		String apiUrl = baseUrl + "/weather?q=" + city + "&appid=" + apiKey;

		ResponseEntity<String> responseEntity = new ResponseEntity<>("Weather data JSON", HttpStatus.OK);

		when(restTemplate.getForEntity(apiUrl, String.class)).thenReturn(responseEntity);

		ResponseEntity<String> result = weatherService.getWeatherForecast(city);
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals("Weather data JSON", result.getBody());
	}

	@Test
	void testGetWeatherForecast_ApiError() {
		String city = "London";
		String apiKey = "test-api-key";
		String baseUrl = "http://test-api.com";
		String apiUrl = baseUrl + "/weather?q=" + city + "&appid=" + apiKey;

		ResponseEntity<String> responseEntity = new ResponseEntity<>("Error message", HttpStatus.NOT_FOUND);

		when(restTemplate.getForEntity(apiUrl, String.class)).thenReturn(responseEntity);

		assertThrows(WeatherForecastException.class, () -> weatherService.getWeatherForecast(city));
	}

}
