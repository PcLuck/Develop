package com.example.weatherApplication.Exception;

public class WeatherForecastException extends RuntimeException {

    public WeatherForecastException(String message) {
        super(message);
    }

    public WeatherForecastException(String message, Throwable cause) {
        super(message, cause);
    }

}
