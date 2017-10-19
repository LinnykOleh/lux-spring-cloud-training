package com.luxoft.training.spring.cloud;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@RestController
public class ProcessingRest {
    private final ProcessingRepository processingRepository;
    private final AccountServiceClient accountServiceClient;
    private final CardServiceClient cardServiceClient;

    public ProcessingRest(ProcessingRepository processingRepository, AccountServiceClient accountServiceClient, CardServiceClient cardServiceClient) {
        this.processingRepository = processingRepository;
        this.accountServiceClient = accountServiceClient;
        this.cardServiceClient = cardServiceClient;
    }

    @RequestMapping("/issue/{accountId}")
    public String issue(@PathVariable("accountId") Integer accountId) {
        String generatedCardNumber = cardServiceClient.create();
        if (generatedCardNumber == null) {
            return "CARD_SERVICE_NOT_AVAILABLE";
        }
        ProcessingEntity processingEntity = new ProcessingEntity();
        processingEntity.setAccountId(accountId);
        processingEntity.setCard(generatedCardNumber);
        ProcessingEntity savedEntity = processingRepository.save(processingEntity);
        accountServiceClient.create(savedEntity.getId());
        return generatedCardNumber;
    }

    @RequestMapping("/checkout/{card}")
    public boolean checkout(@RequestParam("sum") BigDecimal sum, @PathVariable("card") String card) {
        ProcessingEntity processingEntity = processingRepository.findByCard(card);
        if (processingEntity == null) return false;
        return accountServiceClient.checkout(processingEntity.getId(), sum);
    }

    @RequestMapping("/fund/{card}")
    public boolean fund(@RequestParam("sum") BigDecimal sum, @PathVariable("card") String card) {
        ProcessingEntity processingEntity = processingRepository.findByCard(card);
        if (processingEntity == null) return false;
        return accountServiceClient.fund(processingEntity.getId(), sum);
    }

    @RequestMapping("/get")
    public HashMap<Integer, ProcessingEntity> get(@RequestParam("ids") List<Integer> accountIds) {
        HashMap<Integer, ProcessingEntity> map = new HashMap<Integer, ProcessingEntity>();
        List<ProcessingEntity> processingEntities = processingRepository.findByAccountIdIn(accountIds);
        for (ProcessingEntity processingEntity : processingEntities) {
            map.put(processingEntity.getId(), processingEntity);
        }
        return map;
    }
}
