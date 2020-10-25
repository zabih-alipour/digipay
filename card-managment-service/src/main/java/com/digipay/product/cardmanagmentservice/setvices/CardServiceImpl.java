package com.digipay.product.cardmanagmentservice.setvices;

import com.digipay.product.cardmanagmentservice.dtos.SearchDto;
import com.digipay.product.cardmanagmentservice.dtos.TransitionDto;
import com.digipay.product.cardmanagmentservice.exceptions.InvalidInputException;
import com.digipay.product.cardmanagmentservice.models.Card;
import com.digipay.product.cardmanagmentservice.models.Cardex;
import com.digipay.product.cardmanagmentservice.patterns.BankStrategyPattern;
import com.digipay.product.cardmanagmentservice.repositories.CardRepository;
import com.digipay.product.cardmanagmentservice.repositories.CardSpecification;
import com.digipay.product.cardmanagmentservice.sender.Sender;
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

    private final CardexService cardexService;
    private final BankStrategyPattern bankStrategyPattern;
    private final Sender notifySender;

    public CardServiceImpl(CardRepository repository,
                           CardexService cardexService,
                           BankStrategyPattern bankStrategyPattern, Sender notifySender) {
        super(repository);
        this.cardexService = cardexService;
        this.bankStrategyPattern = bankStrategyPattern;
        this.notifySender = notifySender;
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
            if (number.isEmpty() || number.length() > 16 || !number.matches("\\d+")) {
                throw new InvalidInputException("شماره کارت");
            }
        }
        return card;
    }

    @Override
    public boolean transfer(TransitionDto dto) {

        // کاربری که کاربه کارت میکند در سیستم هست و از کارتها موجود در سیستم که متعلق به خودش هست استفاده میکند
        Optional<Card> card = ((CardRepository) repository).findByNumber(dto.getSourceCardNumber());
        return card.map(p -> {
            boolean bankResponse = bankStrategyPattern.getProvider(p.getNumber()).doPayment(dto);
            if (bankResponse) {
                String text = String.format(MESSAGE_TEMPLATE, dto.getAmount(), dto.getSourceCardNumber(), dto.getDistCardNumber());
                notifySender.asSMS(p.getUser().getMobile(), text);
            }
            Cardex cardex = cardexService.transfer(p, dto.getDistCardNumber(), dto.getAmount(), bankResponse);
            return cardex != null;
        }).orElse(Boolean.FALSE);
    }
}
