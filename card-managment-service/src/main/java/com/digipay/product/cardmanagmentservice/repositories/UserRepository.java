package com.digipay.product.cardmanagmentservice.repositories;

import com.digipay.product.cardmanagmentservice.models.Card;
import com.digipay.product.cardmanagmentservice.models.CardStatus;
import com.digipay.product.cardmanagmentservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    User findByUsername(String username);
}
