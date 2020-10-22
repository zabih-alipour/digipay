package com.digipay.product.cardmanagmentservice.repositories;

import com.digipay.product.cardmanagmentservice.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Card findByOwnerName(String ownerName);

    Card findByNumber(String cardNumber);

}
