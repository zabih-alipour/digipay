package com.digipay.product.cardmanagmentservice;

import com.digipay.product.cardmanagmentservice.configs.TestAppConfig;
import com.digipay.product.cardmanagmentservice.dtos.CardBalanceEditDto;
import com.digipay.product.cardmanagmentservice.dtos.CardDto;
import com.digipay.product.cardmanagmentservice.dtos.CardNameDateEditDto;
import com.digipay.product.cardmanagmentservice.dtos.CardOnlinePassEditDto;
import com.digipay.product.cardmanagmentservice.exceptions.CardServiceException;
import com.digipay.product.cardmanagmentservice.exceptions.InvalidInputException;
import com.digipay.product.cardmanagmentservice.models.Card;
import com.digipay.product.cardmanagmentservice.models.CardStatus;
import com.digipay.product.cardmanagmentservice.setvices.CardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import javax.smartcardio.CardException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Note: Method name format:
 * targetMethodNameInService___condition___expectation
 */

@SpringBootTest
@ComponentScan(basePackages = "com.digipay.product.cardmanagmentservice")
@ActiveProfiles("test")
@Import(TestAppConfig.class)
@Sql(scripts = "classpath:downs.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class CardManagementServiceApplicationTests {
    @Autowired
    private CardService cardService;


    /**
     * Note: Method names follows structure below
     * targetMethodNameInService___condition___expectation
     * example:
     * add___normal_card___success
     */


    @Test
    void add___normal_card___success() {
        CardDto cardDto = CardDto.builder()
                .ownerName("ali")
                .expirationDate(LocalDate.now().plusMonths(12))
                .number("6037_9940_4512_4563")
                .onlinePass("5948763")
                .vcc2(4256L)
                .build();
        Card card = cardService.add(cardDto);

        assertThat(card.getId()).isNotNull().isGreaterThan(0);
        assertThat(card.getExpirationDate()).isEqualTo(cardDto.getExpirationDate());
        assertThat(card.getNumber()).isEqualTo(cardDto.getNumber());
        assertThat(card.getCardStatus()).isEqualTo(CardStatus.NEW);
        assertThat(card.getOwnerName()).isEqualTo(cardDto.getOwnerName());
        assertThat(card.getCurrentBalance()).isZero();
    }

    @Test
    void add___ownerName_is_null___fail() {
        CardDto cardDto = CardDto.builder()
                .expirationDate(LocalDate.now().plusMonths(12))
                .number("6037_9940_4512_4563")
                .onlinePass("5948763")
                .vcc2(4256L)
                .build();

        assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(() -> {
                    cardService.add(cardDto);
                })
                .withMessageContaining("not-null property references a null or transient value")
                .withMessageContaining(Card.class.getCanonicalName())
                .withMessageContaining("ownerName");
    }

    @Test
    void add___ownerName_has_number___fail() {
        CardDto cardDto = CardDto.builder()
                .expirationDate(LocalDate.now().plusMonths(12))
                .number("6037_9940_4512_4563")
                .onlinePass("5948763")
                .vcc2(4256L)
                .ownerName("ali123")
                .build();

        assertThatExceptionOfType(InvalidInputException.class)
                .isThrownBy(() -> {
                    cardService.add(cardDto);
                })
                .withMessageContaining("مقدار فیلد(های)")
                .withMessageContaining("صاحب کارت")
                .withMessageContaining("اشتباه است");
    }

    @Test
    void add___balance_is_less_then_zero___fail() {
        CardDto cardDto = CardDto.builder()
                .ownerName("ali")
                .expirationDate(LocalDate.now().plusMonths(12))
                .number("6037_9940_4512_4563")
                .onlinePass("5948763")
                .vcc2(4256L)
                .balance(-12212L)
                .build();

        assertThatExceptionOfType(CardServiceException.class)
                .isThrownBy(() -> {
                    cardService.add(cardDto);
                })
                .withMessageContaining("موجودی نمیتواند کمتر از حداقل (صفر) مقدار باشد");
    }

    @Test
    void add___expirationDate_isNull___fail() {
        CardDto cardDto = CardDto.builder()
                .ownerName("ali")
                .number("6037_9940_4512_4563")
                .onlinePass("5948763")
                .vcc2(4256L)
                .build();

        assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(() -> {
                    cardService.add(cardDto);
                })
                .withMessageContaining("not-null property references a null or transient value")
                .withMessageContaining(Card.class.getCanonicalName())
                .withMessageContaining("expirationDate");
    }

    @Test
    void add___number_isNull___fail() {
        CardDto cardDto = CardDto.builder()
                .ownerName("ali")
                .expirationDate(LocalDate.now().plusMonths(12))
                .onlinePass("5948763")
                .vcc2(4256L)
                .build();

        assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(() -> {
                    cardService.add(cardDto);
                })
                .withMessageContaining("not-null property references a null or transient value")
                .withMessageContaining(Card.class.getCanonicalName())
                .withMessageContaining("number");
    }

    @Test
    void add___onlinePass_isNull___fail() {
        CardDto cardDto = CardDto.builder()
                .ownerName("ali")
                .number("6037_9940_4512_4563")
                .expirationDate(LocalDate.now().plusMonths(12))
                .vcc2(4256L)
                .build();

        assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(() -> {
                    cardService.add(cardDto);
                })
                .withMessageContaining("not-null property references a null or transient value")
                .withMessageContaining(Card.class.getCanonicalName())
                .withMessageContaining("onlinePass");
    }

    @Test
    void add___vcc2_isNull___fail() {
        CardDto cardDto = CardDto.builder()
                .ownerName("ali")
                .number("6037_9940_4512_4563")
                .onlinePass("5948763")
                .expirationDate(LocalDate.now().plusMonths(12))
                .build();

        assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(() -> {
                    cardService.add(cardDto);
                })
                .withMessageContaining("not-null property references a null or transient value")
                .withMessageContaining(Card.class.getCanonicalName())
                .withMessageContaining("vcc2");
    }

    @Test
    void add___number_has_none_numeric_character___fail() {
        CardDto cardDto = CardDto.builder()
                .ownerName("ali")
                .expirationDate(LocalDate.now().plusMonths(12))
                .number("6037we9940_4512_4563")
                .onlinePass("5948763")
                .vcc2(4256L)
                .balance(159753L)
                .build();

        assertThatExceptionOfType(InvalidInputException.class)
                .isThrownBy(() -> {
                    cardService.add(cardDto);
                })
                .withMessageContaining("مقدار فیلد(های)")
                .withMessageContaining("شماره کارت")
                .withMessageContaining("اشتباه است");
    }

    @Test
    void edit___change_ownerName___success() {
        Card card = addACardForEditTests();

        String appendedWord = " Mohammadi";
        assertThat(card.getOwnerName()).doesNotContain(appendedWord);

        CardNameDateEditDto dto = CardNameDateEditDto.builder()
                .ownerName(card.getOwnerName().concat(appendedWord))
                .build();
        card = cardService.edit(card.getId(), dto);

        assertThat(card.getOwnerName()).isNotNull().contains(appendedWord);


    }

    @Test
    void edit___change_balance___success() {
        Card card = addACardForEditTests();
        Long balanceBeforeEdit = card.getCurrentBalance();

        //--- Add
        CardBalanceEditDto dto = CardBalanceEditDto.builder()
                .currentBalance(10000L)
                .operation(CardBalanceEditDto.Operation.ADD)
                .build();
        card = cardService.edit(card.getId(), dto);

        assertThat(card.getCurrentBalance()).isNotNull().isEqualTo(balanceBeforeEdit + dto.getCurrentBalance());

        //--- Subtract
        balanceBeforeEdit = card.getCurrentBalance();
        dto = CardBalanceEditDto.builder()
                .currentBalance(10L)
                .operation(CardBalanceEditDto.Operation.SUBTRACT)
                .build();
        card = cardService.edit(card.getId(), dto);

        assertThat(card.getCurrentBalance()).isNotNull().isEqualTo(balanceBeforeEdit - dto.getCurrentBalance());
    }

    @Test
    void edit___make_balance_to_be_less_than_zero___success() {
        Card card = addACardForEditTests();
        Long balanceBeforeEdit = card.getCurrentBalance();

        //--- Add
        CardBalanceEditDto dto = CardBalanceEditDto.builder()
                .currentBalance(card.getCurrentBalance() + 10)
                .operation(CardBalanceEditDto.Operation.SUBTRACT)
                .build();

        assertThatExceptionOfType(CardServiceException.class)
                .isThrownBy(() -> {
                    cardService.edit(card.getId(), dto);
                })
                .withMessageContaining("موجودی نمیتواند کمتر از حداقل (صفر) مقدار باشد");
    }

    @Test
    void edit___change_onlinePass___success() {
        Card card = addACardForEditTests();

        String onlinePassBeforeEdit = card.getOnlinePass();
        CardOnlinePassEditDto dto = CardOnlinePassEditDto.builder()
                .onlinePass("145236")
                .build();
        card = cardService.edit(card.getId(), dto);

        assertThat(card.getOnlinePass()).isNotNull().isNotEqualTo(onlinePassBeforeEdit);
    }

    private Card addACardForEditTests() {
        CardDto cardDto = CardDto.builder()
                .ownerName("ali")
                .expirationDate(LocalDate.now().plusMonths(12))
                .number("6037_9940_4512_4563")
                .onlinePass("5948763")
                .vcc2(4256L)
                .balance(1000000L)
                .build();
        Card card = cardService.add(cardDto);
        assertThat(card.getId()).isNotNull().isGreaterThan(0);
        return card;
    }

}

