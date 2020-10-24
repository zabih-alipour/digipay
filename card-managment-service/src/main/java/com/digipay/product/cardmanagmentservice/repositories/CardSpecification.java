package com.digipay.product.cardmanagmentservice.repositories;

import com.digipay.product.cardmanagmentservice.models.Card;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class CardSpecification implements Specification<Card> {
    private SearchCriteria criteria;

    public CardSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Card> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (criteria.getOperation() == SearchCriteria.Operation.CONTAIN)
            return criteriaBuilder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
        else if (criteria.getOperation() == SearchCriteria.Operation.EQUAL)
            return criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());
        else if (criteria.getOperation() == SearchCriteria.Operation.GREATER_THAN)
            return criteriaBuilder.greaterThan(root.get(criteria.getKey()), (Long) criteria.getValue());
        else if (criteria.getOperation() == SearchCriteria.Operation.GREATER_THAN_OR_EQUAL)
            return criteriaBuilder.greaterThanOrEqualTo(root.get(criteria.getKey()), (Long) criteria.getValue());
        else if (criteria.getOperation() == SearchCriteria.Operation.LESS_THAN)
            return criteriaBuilder.lessThan(root.get(criteria.getKey()), (Long) criteria.getValue());
        else if (criteria.getOperation() == SearchCriteria.Operation.LESS_THAN_OR_EQUAL)
            return criteriaBuilder.lessThanOrEqualTo(root.get(criteria.getKey()), (Long) criteria.getValue());

        return null;
    }
}
