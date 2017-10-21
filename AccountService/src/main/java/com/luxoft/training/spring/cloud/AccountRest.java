package com.luxoft.training.spring.cloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Autowired
    public AccountRest(AccountDAO accountDAO, AccountRepository accountRepository) {
        this.accountDAO = accountDAO;
        this.accountRepository = accountRepository;
    }

    @PreAuthorize("hasAuthority('ACCOUNT_WRITE')")
    @RequestMapping("/create")
    public void create(@RequestParam("id") Integer id) {
        accountDAO.create(id);
    }

    @PreAuthorize("hasAuthority('ACCOUNT_PROCESS')")
    @RequestMapping("/fund/{id}")
    public boolean fund(@PathVariable("id") Integer id, @RequestParam("sum") BigDecimal sum) {
        return accountDAO.addBalance(id, sum.abs());
    }

    @PreAuthorize("hasAuthority('ACCOUNT_PROCESS')")
    @RequestMapping("/checkout/{id}")
    public boolean checkout(@PathVariable("id") Integer id, @RequestParam("sum") BigDecimal sum) {
        return accountDAO.addBalance(id, sum.abs().negate());
    }

    @PreAuthorize("hasAuthority('ACCOUNT_READ')")
    @RequestMapping("/get/{clientId}")
    public List<AccountEntity> get(@PathVariable("clientId") Integer clientId) {
        return accountRepository.findByClientId(clientId);
    }
}
