package com.sprinboot.webflux.msauthserver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean("restTemplate")
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
