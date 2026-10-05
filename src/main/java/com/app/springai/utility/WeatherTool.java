package com.app.springai.utility;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

public class WeatherTool {

    @Value("${app.weather.api.key}")
    private String weatherAPIKey;

    private RestClient restClient;

    public WeatherTool(RestClient restClient) {
        this.restClient = restClient;

    }

    @Tool(description = "Get the current weather for a given city")
    public String getWeather(@ToolParam(description = "The city for which to get weather information") String city){

        URI uri = UriComponentsBuilder
                .fromUriString("http://api.weatherapi.com")
                .path("/v1/current.json")
                .queryParam("key", "ENTER KEY HERE")
                .queryParam("q", city)
                .queryParam("aqi", "yes")
                .build()
                .toUri();

        System.out.println("FULL URL: " + uri);

        var response = restClient.get()
                .uri(uri)
                .retrieve()
                .body(new ParameterizedTypeReference<Map<String, Object>>() {});

        return response.toString();
    }

}
