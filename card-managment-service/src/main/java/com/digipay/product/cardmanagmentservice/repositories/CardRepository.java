package com.digipay.product.cardmanagmentservice.repositories;

import com.digipay.product.cardmanagmentservice.models.Card;
import com.digipay.product.cardmanagmentservice.models.CardStatus;
import com.digipay.product.cardmanagmentservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long>, JpaSpecificationExecutor<Card> {

    Card findByNumber(String cardNumber);

    List<Card> findByUser(User user);

    Card findByNumberAndUser(String cardNumber, User user);

    default boolean canWithdraw(String cardNumber, Long amount) {
        return canWithdraw(cardNumber, amount, CardStatus.ACTIVE);
    }

    @Query("select case " +
            "when count(c) > 0 then true " +
            "else false end " +
            "from Card c " +
            "where c.number=:cardNumber " +
            "and (c.currentBalance - :amount) >= 0 " +
            "and c.cardStatus = :status")
    boolean canWithdraw(@Param("cardNumber") String cardNumber, @Param("amount") Long amount, CardStatus status);
}
