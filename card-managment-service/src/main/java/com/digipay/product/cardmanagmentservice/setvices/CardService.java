package com.digipay.product.cardmanagmentservice.setvices;

import com.digipay.product.cardmanagmentservice.dtos.TransitionDto;
import com.digipay.product.cardmanagmentservice.models.Card;

public interface CardService extends ParentService<Card, Long> {
    String MESSAGE_TEMPLATE = "کاربر گرامی:\nانتقال وجه کارت به کارت به مبلغ %d از کارت بانکی %s به کارت مقصد %s با موفقیت انجام شد.";

    boolean transfer(TransitionDto dto);
}
