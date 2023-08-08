package com.example.weatherApplication.weatherforcastservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.example.weatherApplication.Exception.WeatherForecastException;
import java.util.concurrent.TimeUnit;

@Service
public class WeatherForCastService {

	@Value("${weather.api.base-url}")
	private String baseUrl;

	@Value("${weather.api.key}")
	private String apiKey;

	private final RestTemplate restTemplate = new RestTemplate();
	private final Cache<String, ResponseEntity<String>> weatherCache = CacheBuilder.newBuilder()
			.expireAfterWrite(30, TimeUnit.MINUTES) // Set TTL to 30 minutes
			.build();

	public ResponseEntity<String> getWeatherForecast(String city) {
		try {
			return weatherCache.get(city, () -> fetchWeatherData(city));
		} catch (Exception e) {
			throw new WeatherForecastException("Error while fetching weather data", e);
		}
	}

	private ResponseEntity<String> fetchWeatherData(String city) {
		String apiUrl = baseUrl + "/weather?q=" + city + "&appid=" + apiKey;
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);

		if (!responseEntity.getStatusCode().is2xxSuccessful()) {
			throw new WeatherForecastException("Failed to retrieve weather data: " + responseEntity.getStatusCode());
		}

		return responseEntity;
	}
}
