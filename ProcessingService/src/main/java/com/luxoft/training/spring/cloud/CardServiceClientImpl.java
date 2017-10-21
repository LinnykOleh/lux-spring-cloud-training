package com.luxoft.training.spring.cloud;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Component;

@Component
public class CardServiceClientImpl implements CardServiceClient {
    private final OAuth2RestTemplate oAuth2RestTemplate;

    @Autowired
    public CardServiceClientImpl(@Qualifier("oAuth2CardService") OAuth2RestTemplate oAuth2RestTemplate) {
        this.oAuth2RestTemplate = oAuth2RestTemplate;
    }

    @HystrixCommand(fallbackMethod = "fallback")
    @Override
    public String create() {
        return oAuth2RestTemplate.getForObject("http://CardService/create", String.class);
    }

    private String fallback() {
        return null;
    }
}
