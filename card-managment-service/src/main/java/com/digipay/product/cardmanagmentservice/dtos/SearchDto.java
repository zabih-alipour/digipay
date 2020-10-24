package com.digipay.product.cardmanagmentservice.dtos;

import com.digipay.product.cardmanagmentservice.repositories.SearchCriteria;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class SearchDto implements Serializable {
    private int page = 0;
    private int pageSize = 50;

    private List<SearchCriteria> searchCriteria;
}
