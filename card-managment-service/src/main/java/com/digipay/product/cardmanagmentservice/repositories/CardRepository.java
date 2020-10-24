package com.digipay.product.cardmanagmentservice.repositories;

import com.digipay.product.cardmanagmentservice.models.Card;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends ParentRepository<Card, Long> {
    Optional<Card> findByNumber(String cardNumber);
}
