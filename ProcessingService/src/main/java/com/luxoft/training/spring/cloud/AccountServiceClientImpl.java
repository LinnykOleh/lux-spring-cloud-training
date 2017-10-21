package com.luxoft.training.spring.cloud;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AccountServiceClientImpl implements AccountServiceClient {

    private final OAuth2RestTemplate oAuth2RestTemplate;

    @Autowired
    public AccountServiceClientImpl(OAuth2RestTemplate oAuth2RestTemplate) {
        this.oAuth2RestTemplate = oAuth2RestTemplate;
    }

    private String getToken() {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext()
                                                                                                 .getAuthentication()
                                                                                                 .getDetails();
        return details.getTokenValue();
    }

    @HystrixCommand(fallbackMethod = "fallback")
    @Override
    public boolean checkout(Integer parentId, BigDecimal sum) {
        return executeOperation(parentId, sum, "checkout");
    }

    @HystrixCommand(fallbackMethod = "fallback")
    @Override
    public boolean fund(Integer parentId, BigDecimal sum) {
        return executeOperation(parentId, sum, "fund");
    }

    @Override
    public void create(Integer id) {
        updateToken();
        oAuth2RestTemplate.getForObject("http://AccountService/create?id=" + id, Void.class);
    }

    private boolean executeOperation(Integer parentId, BigDecimal sum, String checkout) {
        updateToken();

        return oAuth2RestTemplate.getForObject(
                "http://AccountService/" + checkout + "/" + parentId + "?sum=" + sum.toPlainString(), Boolean.class);
    }

    private void updateToken() {
        oAuth2RestTemplate.getOAuth2ClientContext().setAccessToken(new DefaultOAuth2AccessToken(getToken()));
    }

    private boolean fallback(Integer parentId, BigDecimal sum) {
        return false;
    }
}
