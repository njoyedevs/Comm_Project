package com.njoye.comm.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.njoye.comm.config.GiphyConfig;

@RestController
public class GiphyController {

    private final GiphyConfig giphyConfig;

    public GiphyController(GiphyConfig giphyConfig) {
        this.giphyConfig = giphyConfig;
    }

    @GetMapping("/api/giphy-search")
    public ResponseEntity<String> searchGiphy(@RequestParam("q") String searchTerm) {
        String apiKey = giphyConfig.getApiKey();
        String url = String.format("https://api.giphy.com/v1/gifs/search?api_key=%s&q=%s", apiKey, searchTerm);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }
}