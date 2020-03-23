package com.example.movie.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class PythonService {

    @Autowired
    @Qualifier("nonSecureRestTemplate")
    private RestTemplate restTemplate;

    public String analyse(String comment, String description) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> fieldMap = new HashMap<>();
        fieldMap.put("comment", comment);
        fieldMap.put("description", description);

        StringBuilder json = new StringBuilder();

        try {
            ObjectMapper mapper = new ObjectMapper();
            json.append(mapper.writeValueAsString(fieldMap));
        } catch (JsonProcessingException e) {

        }

        ResponseEntity<String> response = restTemplate.exchange(
                "http://127.0.0.1:5000/data",
                HttpMethod.POST, new HttpEntity<>(json.toString(), headers), String.class);


        return response.getBody();
    }

}
