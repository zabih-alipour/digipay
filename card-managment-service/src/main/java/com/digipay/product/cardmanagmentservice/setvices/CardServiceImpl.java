package com.digipay.product.cardmanagmentservice.setvices;

import com.digipay.product.cardmanagmentservice.dtos.ParentDto;
import com.digipay.product.cardmanagmentservice.dtos.SearchDto;
import com.digipay.product.cardmanagmentservice.exceptions.InvalidInputException;
import com.digipay.product.cardmanagmentservice.models.Card;
import com.digipay.product.cardmanagmentservice.repositories.CardRepository;
import com.digipay.product.cardmanagmentservice.repositories.CardSpecification;
import com.digipay.product.cardmanagmentservice.repositories.CardexRepository;
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
public class CardServiceImpl extends ParentServiceImpl<Card, Long> implements CardService {
    private final UserRepository userRepository;
    private final CardexRepository cardexRepository;

    public CardServiceImpl(CardRepository repository, UserRepository userRepository, CardexRepository cardexRepository) {
        super(repository);
        this.userRepository = userRepository;
        this.cardexRepository = cardexRepository;
    }

    @Override
    public Page<Card> find(SearchDto searchDto) {
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

    @Override
    protected Card validate(Card card) {
        if (card.getNumber() != null) {
            String number = card.getNumber().replace("_", "");
            if (number.isEmpty() || !number.matches("\\d+")) {
                throw new InvalidInputException("شماره کارت");
            }
        }

        return card;
    }


}
