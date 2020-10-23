package com.digipay.product.cardmanagmentservice.setvices;

import com.digipay.product.cardmanagmentservice.dtos.CardDto;
import com.digipay.product.cardmanagmentservice.dtos.CardEditDto;
import com.digipay.product.cardmanagmentservice.dtos.SearchDto;
import com.digipay.product.cardmanagmentservice.exceptions.CardServiceException;
import com.digipay.product.cardmanagmentservice.exceptions.InvalidInputException;
import com.digipay.product.cardmanagmentservice.models.Card;
import com.digipay.product.cardmanagmentservice.models.User;
import com.digipay.product.cardmanagmentservice.repositories.CardRepository;
import com.digipay.product.cardmanagmentservice.repositories.CardSpecification;
import com.digipay.product.cardmanagmentservice.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CardService {
    private final CardRepository repository;
    private final UserRepository userRepository;

    public CardService(CardRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
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

    public void delete(String cardNumber, Long userId) {
        Card card = repository.findByNumberAndUser(cardNumber, User.builder().id(userId).build());
        repository.delete(card);
    }

    public Page<Card> findPaginated(SearchDto searchDto) {
        List<CardSpecification> collect = Optional.ofNullable(searchDto.getSearchCriteria())
                .map(criteria -> criteria.stream()
                        .map(CardSpecification::new)
                        .collect(Collectors.toList()))
                .orElseGet(ArrayList::new);

        Specification<Card> specification = null;
        for (CardSpecification cardSpecification : collect) {
            if (specification == null)
                specification = cardSpecification;
            else
                specification.and(cardSpecification);
        }

        return repository.findAll(specification, PageRequest.of(searchDto.getPage(), searchDto.getPageSize()));
    }

    public List<Card> list() {
        return repository.findAll();
    }

    public boolean canWithdraw(String cardNumber, Long amount) {
        return repository.canWithdraw(cardNumber, amount);
    }

    public void withdraw(String cardNumber, Long amount) {
        if (repository.canWithdraw(cardNumber, amount)) {
            Card card = repository.findByNumber(cardNumber);
            card.setCurrentBalance(card.getCurrentBalance() - amount);
            repository.save(card);
        } else
            throw new CardServiceException("موجودی کافی نمیباشد");
    }

    private void validateCard(Card card) {
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
