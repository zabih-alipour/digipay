package com.digipay.product.cardmanagmentservice.repositories;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class SearchCriteria {
    private String key;
    private Operation operation;
    private Object value;

    public enum Operation {
        EQUAL, CONTAIN, LESS_THAN, GREATER_THAN, LESS_THAN_OR_EQUAL, GREATER_THAN_OR_EQUAL
    }

    public SearchCriteria() {
    }
}
