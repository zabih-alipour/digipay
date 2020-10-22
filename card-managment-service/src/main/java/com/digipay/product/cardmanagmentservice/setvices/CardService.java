package com.digipay.product.cardmanagmentservice.setvices;

import com.digipay.product.cardmanagmentservice.dtos.CardDto;
import com.digipay.product.cardmanagmentservice.dtos.CardEditDto;
import com.digipay.product.cardmanagmentservice.exceptions.CardServiceException;
import com.digipay.product.cardmanagmentservice.exceptions.InvalidInputException;
import com.digipay.product.cardmanagmentservice.models.Card;
import com.digipay.product.cardmanagmentservice.repositories.CardRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CardService {
    private final CardRepository repository;

    public CardService(CardRepository repository) {
        this.repository = repository;
    }

    public Card add(CardDto cardDto) {
        Card card = cardDto.getEntity();
        validateCard(card);
        return repository.save(card);
    }

    public Card edit(Long id, CardEditDto cardEditDto) {
        Card card = repository.getOne(id);
        card = cardEditDto.merge(card);

        validateCard(card);

        return repository.save(card);
    }

    public List<Card> list() {
        return repository.findAll();
    }

    private void validateCard(Card card) {
        String ownerName = card.getOwnerName();
        if (ownerName != null) {
            if (ownerName.isEmpty() || ownerName.matches(".*\\d.*")) {
                throw new InvalidInputException("صاحب کارت");
            }
        }

        if (card.getNumber() != null) {
            String number = card.getNumber().replace("_", "");
            if (number.isEmpty() || !number.matches("\\d+")) {
                throw new InvalidInputException("شماره کارت");
            }
        }

        if (card.getCurrentBalance() != null && card.getCurrentBalance() < 0) {
            throw new CardServiceException("موجودی نمیتواند کمتر از حداقل (صفر) مقدار باشد");
        }
    }
}
