package com.digipay.product.cardmanagmentservice.repositories;

import com.digipay.product.cardmanagmentservice.models.Cardex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CardexRepository extends JpaRepository<Cardex, Long>, JpaSpecificationExecutor<Cardex> {

}
