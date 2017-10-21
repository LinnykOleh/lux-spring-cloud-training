package com.luxoft.training.spring.cloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class AccountRest {
    private final AccountDAO accountDAO;
    private final AccountRepository accountRepository;
    private final RemoteEventPublisher remoteEventPublisher;

    @Autowired
    public AccountRest(AccountDAO accountDAO, AccountRepository accountRepository, RemoteEventPublisher remoteEventPublisher) {
        this.accountDAO = accountDAO;
        this.accountRepository = accountRepository;
        this.remoteEventPublisher = remoteEventPublisher;
    }

    @RequestMapping("/create")
    public void create(@RequestParam("id") Integer id) {
        accountDAO.create(id);
    }

    @RequestMapping("/fund/{id}")
    public boolean fund(@PathVariable("id") Integer id, @RequestParam("sum") BigDecimal sum) {
        remoteEventPublisher.publishEvent(new FundEvent("AccountService", "HistoryService", sum.abs()));
        return accountDAO.addBalance(id, sum.abs());
    }

    @RequestMapping("/checkout/{id}")
    public boolean checkout(@PathVariable("id") Integer id, @RequestParam("sum") BigDecimal sum) {
        remoteEventPublisher.publishEvent(new WithdrawEvent("AccountService", "HistoryService", sum.abs().negate()));
        return accountDAO.addBalance(id, sum.abs().negate());
    }

    @RequestMapping("/get/{clientId}")
    public List<AccountEntity> get(@PathVariable("clientId") Integer clientId) {
        return accountRepository.findByClientId(clientId);
    }
}
