package com.digipay.product.cardmanagmentservice.setvices;

import com.digipay.product.cardmanagmentservice.dtos.EditDto;
import com.digipay.product.cardmanagmentservice.dtos.ReportDto;
import com.digipay.product.cardmanagmentservice.models.Card;
import com.digipay.product.cardmanagmentservice.models.Cardex;
import com.digipay.product.cardmanagmentservice.models.TransferStatus;
import com.digipay.product.cardmanagmentservice.repositories.CardexRepository;
import org.springframework.stereotype.Service;

import javax.transaction.NotSupportedException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class CardexServiceImpl extends ParentServiceImpl<Cardex, Long> implements CardexService {
    public CardexServiceImpl(CardexRepository repository) {
        super(repository);
    }

    @Override
    public Cardex edit(Long id, EditDto<Cardex> dto) throws Exception {
        throw new NotSupportedException("عملیات مجاز نمیباشد");
    }

    @Override
    public void delete(Long id) throws Exception {
        throw new NotSupportedException("عملیات مجاز نمیباشد");
    }

    @Override
    public Cardex transfer(Card card, String distCardNumber, Long amount, boolean isSuccess) {
        Cardex cardex = Cardex.builder()
                .user(card.getUser())
                .sourceCard(card.getNumber())
                .destCard(distCardNumber)
                .amount(amount)
                .transferStatus(isSuccess ? TransferStatus.SUCCESS : TransferStatus.FAIL)
                .build();
        Cardex cardex1 = repository.save(cardex);

        // TODO: 10/24/20 Call rabbitMQ for send notification to sender user
        return cardex1;
    }

    @Override
    public List<ReportDto> report(LocalDate from, LocalDate to) {
        return ((CardexRepository)repository).getReportDetails(from, to);
    }
}
