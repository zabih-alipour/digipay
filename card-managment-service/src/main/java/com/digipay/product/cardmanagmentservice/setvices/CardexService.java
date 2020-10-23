package com.digipay.product.cardmanagmentservice.setvices;

import com.digipay.product.cardmanagmentservice.repositories.CardRepository;
import com.digipay.product.cardmanagmentservice.repositories.CardexRepository;
import com.digipay.product.cardmanagmentservice.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CardexService {
    private final CardRepository repository;
    private final UserRepository userRepository;
    private final CardexRepository cardexRepository;

    public CardexService(CardRepository repository, UserRepository userRepository, CardexRepository cardexRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.cardexRepository = cardexRepository;
    }


}
