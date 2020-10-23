package com.digipay.product.cardmanagmentservice.repositories;

import com.digipay.product.cardmanagmentservice.models.Card;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends ParentRepository<Card, Long> {

}
