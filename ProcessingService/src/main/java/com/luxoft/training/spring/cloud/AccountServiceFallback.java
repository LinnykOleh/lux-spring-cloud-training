package com.luxoft.training.spring.cloud;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AccountServiceFallback implements AccountServiceClient {
    @Override
    public boolean checkout(Integer parentId, BigDecimal sum) {
        return false;
    }

    @Override
    public boolean fund(Integer parentId, BigDecimal sum) {
        return false;
    }

    @Override
    public void create(Integer id) {
    }
}
