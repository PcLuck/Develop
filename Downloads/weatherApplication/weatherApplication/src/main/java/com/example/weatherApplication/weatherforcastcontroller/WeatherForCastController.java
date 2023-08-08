package com.example.weatherApplication.weatherforcastcontroller;

import com.example.weatherApplication.Exception.WeatherForecastException;
import com.example.weatherApplication.weatherforcastservice.WeatherForCastService;

import org.springframework.http.HttpHeaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherForCastController {

    @Autowired
    private WeatherForCastService weatherService;

    @GetMapping(value = "/{city}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getWeatherForecast(@PathVariable String city) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            try {
                ResponseEntity<String> responseEntity = weatherService.getWeatherForecast(city);
                return new ResponseEntity<>(responseEntity.getBody(), headers, responseEntity.getStatusCode());
            } catch (WeatherForecastException e) {
                return ResponseEntity.status(500).body("{\"error\": \"" + e.getMessage() + "\"}");
            }
        }
    }

