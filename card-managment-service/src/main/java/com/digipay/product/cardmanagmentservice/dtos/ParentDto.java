package com.digipay.product.cardmanagmentservice.dtos;

import com.digipay.product.cardmanagmentservice.models.ParentEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Optional;

@Setter
@Getter
@SuperBuilder
public class ParentDto<T extends ParentEntity> {

    public ParentDto() {
    }

    public Optional<T> entityInstance() {
        return Optional.empty();
    }
}
