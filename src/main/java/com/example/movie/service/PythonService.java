package com.example.movie.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class PythonService {

    private final Logger log = LoggerFactory.getLogger(PythonService.class);

    @Autowired
    @Qualifier("nonSecureRestTemplate")
    private RestTemplate restTemplate;

    /**
     * Add new movie to the database with the trailer URL
     *
     * @param comment one comment of a movie
     * @param description YouTube movie description
     *
     * @return Python response
     */
    public String analyse(String comment, String description, String title) {
        log.info("PythonService.analyse() comment {}, description {}, title {}, ", comment, description, title);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> fieldMap = new HashMap<>();
        fieldMap.put("comment", comment);
        fieldMap.put("description", description);
        fieldMap.put("title", title);

        StringBuilder json = new StringBuilder();

        try {
            ObjectMapper mapper = new ObjectMapper();
            json.append(mapper.writeValueAsString(fieldMap));
        } catch (JsonProcessingException e) {

        }

        // call Python rest API
        ResponseEntity<String> response = restTemplate.exchange(
                "http://127.0.0.1:5000/data",
                HttpMethod.POST, new HttpEntity<>(json.toString(), headers), String.class);

        log.info("PythonService response {}", response.getBody());
        return response.getBody();
    }

}
