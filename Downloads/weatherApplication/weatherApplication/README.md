# Weather Forecast Application

This is a simple weather forecast application built using Spring Boot that consumes a public REST API service to provide weather data for a specified city.

## Prerequisites

- Java Development Kit (JDK) 8 or later
- Apache Maven
- Add the tomcat server (If not working on spring boot application)

## Getting Started

1. Clone the repository or download the source code:

git clone https://github.com/your-username/weather-forecast.git
cd weather-forecast

1. API key should be changed as per the weather api being used in the project.

Open the src/main/resources/application.properties file and replace 	YOUR_API_KEY with your actual API key from the weather service 	provider (e.g., OpenWeatherMap).

2. Build and RUn the application using Maven:
	mvn clean install
	
3. The application will start and be accessible at http://localhost:8080.
   
4. Usage Open a web browser or use a tool like Postman.

Below is the URL to make a GET api call in order to fetch the weatherforecast based on city name provided by user: http://localhost:8080/weather/{city}

The response will be in JSON format, providing weather information for the specified city.

5. Error Handling

The application includes error handling for cases where the weather data cannot be   retrieved or when an invalid city name is provided.

6. Contributing

Feel free to clone the repository and submit pull requests or open issues for any improvements or fixes.

