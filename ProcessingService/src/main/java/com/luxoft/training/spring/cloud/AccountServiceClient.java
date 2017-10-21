package com.luxoft.training.spring.cloud;

import java.math.BigDecimal;

public interface AccountServiceClient {
    boolean checkout(Integer parentId, BigDecimal sum);

    boolean fund(Integer parentId, BigDecimal sum);

    void create(Integer id);
}
