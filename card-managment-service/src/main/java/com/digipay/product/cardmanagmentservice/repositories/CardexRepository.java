package com.digipay.product.cardmanagmentservice.repositories;

import com.digipay.product.cardmanagmentservice.dtos.ReportDto;
import com.digipay.product.cardmanagmentservice.models.Cardex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CardexRepository extends ParentRepository<Cardex, Long> {

    @Query(nativeQuery = true)
    List<ReportDto> getReportDetails(@Param("from") LocalDate from, @Param("to") LocalDate to);
}
