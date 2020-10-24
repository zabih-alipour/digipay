package com.digipay.product.cardmanagmentservice.setvices;

import com.digipay.product.cardmanagmentservice.dtos.TransitionDto;
import com.digipay.product.cardmanagmentservice.models.Card;

public interface CardService extends ParentService<Card, Long> {

    boolean transfer(TransitionDto dto);
}