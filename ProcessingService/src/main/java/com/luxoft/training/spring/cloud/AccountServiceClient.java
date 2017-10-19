package com.luxoft.training.spring.cloud;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(value = "AccountService", fallback = AccountServiceFallback.class)
public interface AccountServiceClient {
    @RequestMapping("/checkout/{id}")
    boolean checkout(@PathVariable("id") Integer parentId, @RequestParam("sum") BigDecimal sum);

    @RequestMapping("/fund/{id}")
    boolean fund(@PathVariable("id") Integer parentId, @RequestParam("sum") BigDecimal sum);

    @RequestMapping("/create")
    void create(@RequestParam("id") Integer id);
}
