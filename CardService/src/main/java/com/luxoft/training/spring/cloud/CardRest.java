package com.luxoft.training.spring.cloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardRest {
    private final CardNumberGenerator cardNumberGenerator;

    @Autowired
    public CardRest(CardNumberGenerator cardNumberGenerator) {
        this.cardNumberGenerator = cardNumberGenerator;
    }

    @RequestMapping("/create")
    public String create() {
        return cardNumberGenerator.generate();
    }
}
