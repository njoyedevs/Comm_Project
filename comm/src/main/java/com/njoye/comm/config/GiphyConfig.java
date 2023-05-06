package com.njoye.comm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GiphyConfig {

    @Value("${giphy.api_key}")
    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }
}