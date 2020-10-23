package com.digipay.product.cardmanagmentservice.dtos;

import com.digipay.product.cardmanagmentservice.models.Card;
import com.digipay.product.cardmanagmentservice.models.ParentEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Optional;

@Setter
@Getter
@SuperBuilder
public class EditDto<T extends ParentEntity> extends ParentDto<T> {

    public EditDto() {
    }

    public Optional<T> merge(T t) {
        return Optional.ofNullable(t);
    }
}
