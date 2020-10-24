package com.digipay.product.cardmanagmentservice.setvices;

import com.digipay.product.cardmanagmentservice.dtos.ReportDto;
import com.digipay.product.cardmanagmentservice.models.Card;
import com.digipay.product.cardmanagmentservice.models.Cardex;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface CardexService extends ParentService<Cardex, Long> {

    Cardex transfer(Card card, String distCardNumber, Long amount, boolean isSuccess);

    List<ReportDto> report(LocalDate from, LocalDate to);
}
