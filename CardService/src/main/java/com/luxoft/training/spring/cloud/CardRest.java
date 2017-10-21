package com.luxoft.training.spring.cloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardRest {
    private final CardNumberGenerator cardNumberGenerator;

    @Autowired
    public CardRest(CardNumberGenerator cardNumberGenerator) {
        this.cardNumberGenerator = cardNumberGenerator;
    }

    @PreAuthorize("hasAuthority('CARD_WRITE')")
    @RequestMapping("/create")
    public String create() {
        return cardNumberGenerator.generate();
    }
}
