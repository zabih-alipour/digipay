package com.digipay.product.cardmanagmentservice.setvices;

import com.digipay.product.cardmanagmentservice.models.Card;
import com.digipay.product.cardmanagmentservice.repositories.CardRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CardService {
    private CardRepository repository;

    public CardService(CardRepository repository) {
        this.repository = repository;
    }

    public List<Card> list(){
        return repository.findAll();
    }
}
