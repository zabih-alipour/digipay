package com.digipay.product.cardmanagmentservice.utils;

import com.digipay.product.cardmanagmentservice.dtos.CardDto;
import com.digipay.product.cardmanagmentservice.models.Card;
import com.digipay.product.cardmanagmentservice.models.User;
import com.digipay.product.cardmanagmentservice.repositories.UserRepository;
import com.digipay.product.cardmanagmentservice.setvices.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

@Component
public class DataUtil {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CardService cardService;
    private final String defaultDatabaseUsername = "ali";

    public Card commonCardForTests() {
        User user = userRepository.findByUsername(defaultDatabaseUsername);
        CardDto cardDto = CardDto.builder()
                .user(user)
                .number(randomCardNumber())
                .build();
        Card card = cardService.add(cardDto);
        assertThat(card.getId()).isNotNull().isGreaterThan(0);
        return card;
    }

    public String randomCardNumber() {
        List<String> givenList = Arrays.asList("6037", "5022", "9797");
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        return givenList.get(threadLocalRandom.nextInt(givenList.size())) +
                threadLocalRandom.nextInt(1000, 9999) +
                threadLocalRandom.nextInt(1000, 9999) +
                threadLocalRandom.nextInt(1000, 9999);
    }
}
