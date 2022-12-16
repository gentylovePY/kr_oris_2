package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Weather {
    public Map<String, String> getRequestWeather(String city) throws IOException {
        URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=50da205a9c76cfaf41a554bc57768910");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        StringBuilder result = new StringBuilder();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(1000);
        connection.setReadTimeout(1000);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String input;
            while ((input = reader.readLine()) != null) {
                result.append(input);
            }
        }
        connection.disconnect();
        return parseJson(result.toString());
    }

    public Map<String, String> parseJson(String json) throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);
        int temp = (int) (jsonNode.get("main").get("temp").asDouble() - 273.15);
        int tempMin = (int) (jsonNode.get("main").get("temp_min").asDouble() - 273.15);
        int tempMax = (int) (jsonNode.get("main").get("temp_max").asDouble() - 273.15);
        map.put("temp", String.valueOf(temp));
        map.put("temp_min", String.valueOf(tempMin));
        map.put("temp_max", String.valueOf(tempMax));
        map.put("name", jsonNode.get("name").asText());
        
        return map;
    }
}

