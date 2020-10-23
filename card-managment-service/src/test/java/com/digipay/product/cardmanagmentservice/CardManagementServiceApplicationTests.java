package com.digipay.product.cardmanagmentservice;

import com.digipay.product.cardmanagmentservice.configs.TestAppConfig;
import com.digipay.product.cardmanagmentservice.dtos.*;
import com.digipay.product.cardmanagmentservice.exceptions.CardServiceException;
import com.digipay.product.cardmanagmentservice.exceptions.InvalidInputException;
import com.digipay.product.cardmanagmentservice.models.Card;
import com.digipay.product.cardmanagmentservice.models.CardStatus;
import com.digipay.product.cardmanagmentservice.models.User;
import com.digipay.product.cardmanagmentservice.repositories.UserRepository;
import com.digipay.product.cardmanagmentservice.setvices.CardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

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
    @Autowired
    private UserRepository userRepository;
    private String defaultDatabaseUsername = "ali";

    /**
     * Note: Method names follows structure below
     * targetMethodNameInService___condition___expectation
     * example:
     * add___normal_card___success
     */

    @BeforeEach
    public void setup() {
        User user = User.builder()
                .name("kobra")
                .username("kobi")
                .password("kkhs98as")
                .mobile("09025845638")
                .build();
        userRepository.save(user);
        //-- card 1
        CardDto cardDto_1 = CardDto.builder()
                .user(user)
                .expirationDate(LocalDate.now().plusMonths(12))
                .number(randomCardNumber())
                .onlinePass("5948763")
                .vcc2(4256L)
                .balance(10000000L)
                .build();
        cardService.add(cardDto_1);
        //-- card 2
        CardDto cardDto_2 = CardDto.builder()
                .user(user)
                .expirationDate(LocalDate.now().plusMonths(12))
                .number(randomCardNumber())
                .onlinePass("5948763")
                .vcc2(4256L)
                .balance(4555555L)
                .build();
        cardService.add(cardDto_2);

    }

    @Test
    void add___normal_card___success() {
        User user = userRepository.findByUsername(defaultDatabaseUsername);
        CardDto cardDto = CardDto.builder()
                .user(user)
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
        assertThat(card.getUser().getId()).isEqualTo(cardDto.getUser().getId());
        assertThat(card.getCurrentBalance()).isZero();
    }

    @Test
    void add___user_is_null___fail() {
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
                .withMessageContaining("user");
    }

    @Test
    void add___balance_is_less_then_zero___fail() {
        User user = userRepository.findByUsername(defaultDatabaseUsername);
        CardDto cardDto = CardDto.builder()
                .user(user)
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
        User user = userRepository.findByUsername(defaultDatabaseUsername);
        CardDto cardDto = CardDto.builder()
                .user(user)
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
        User user = userRepository.findByUsername(defaultDatabaseUsername);
        CardDto cardDto = CardDto.builder()
                .user(user)
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
        User user = userRepository.findByUsername(defaultDatabaseUsername);
        CardDto cardDto = CardDto.builder()
                .user(user)
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
        User user = userRepository.findByUsername(defaultDatabaseUsername);
        CardDto cardDto = CardDto.builder()
                .user(user)
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
        User user = userRepository.findByUsername(defaultDatabaseUsername);
        CardDto cardDto = CardDto.builder()
                .user(user)
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
    void edit___change_balance___success() {
        Card card = commonCardForTests();
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
    void edit___make_balance_to_be_less_than_zero___fail() {
        Card card = commonCardForTests();
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
        Card card = commonCardForTests();
        String onlinePassBeforeEdit = card.getOnlinePass();
        CardOnlinePassEditDto dto = CardOnlinePassEditDto.builder()
                .onlinePass("145236")
                .build();
        card = cardService.edit(card.getId(), dto);

        assertThat(card.getOnlinePass()).isNotNull().isNotEqualTo(onlinePassBeforeEdit);
    }

    @Test
    void delete___unused_card___success() {
        Card card = commonCardForTests();
        int cardSizeBeforeDelete = cardService.list().size();

        cardService.delete(card.getNumber(), card.getUser().getId());
        assertThat(cardService.list().size()).isLessThan(cardSizeBeforeDelete).isEqualTo(cardSizeBeforeDelete - 1);
    }

    private String randomCardNumber() {

        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        return "6037" +
                "_" +
                threadLocalRandom.nextInt(1000, 9999) +
                "_" +
                threadLocalRandom.nextInt(1000, 9999) +
                "_" +
                threadLocalRandom.nextInt(1000, 9999);

    }

    @Test
    void findPaginated___search_by_name___success() {
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        userRepository.findAll().forEach(user -> {
            CardDto cardDto1 = CardDto.builder()
                    .user(user)
                    .expirationDate(LocalDate.now().plusMonths(12))
                    .number(randomCardNumber())
                    .onlinePass("5948763")
                    .vcc2((long) threadLocalRandom.nextInt(10000, 90999))
                    .balance((long) threadLocalRandom.nextInt(1000, 999999))
                    .build();
            Card card1 = cardService.add(cardDto1);
            assertThat(card1.getId()).isNotNull().isGreaterThan(0);
        });

        SearchDto searchDto = new SearchDto();
        searchDto.setPageSize(2);
        Page<Card> paginated = cardService.findPaginated(searchDto);
        assertThat(paginated.getTotalElements()).isGreaterThan(1);
    }

    @Test
    void canWithdraw___amount_greater_than_balance___fail() {
        Card card = commonCardForTests();
        boolean result = cardService.canWithdraw(card.getNumber(), card.getCurrentBalance() * 10);
        assertThat(result).isFalse();
    }

    @Test
    void canWithdraw___amount_less_than_balance___success() {
        Card card = commonCardForTests();
        boolean result = cardService.canWithdraw(card.getNumber(), card.getCurrentBalance() / 10);
        assertThat(result).isTrue();
    }

    @Test
    void add___number_has_more_than_16_digit___fail() {
        // TODO: 10/23/20 Implement test and business layer
    }

    @Test
    void add___set_none_number_to_vcc2___fail() {
        // TODO: 10/23/20 Implement test and business layer
    }

    @Test
    void add___set_expiration_date_to_passed_days___fail() {
        // TODO: 10/23/20 Implement test and business layer
    }

    private Card commonCardForTests() {
        User user = userRepository.findByUsername(defaultDatabaseUsername);
        CardDto cardDto = CardDto.builder()
                .user(user)
                .expirationDate(LocalDate.now().plusMonths(12))
                .number("6037_9940_4512_4563")
                .onlinePass("5948763")
                .vcc2(4256L)
                .balance(1000000L)
                .build();
        Card card = cardService.add(cardDto);
        assertThat(card.getId()).isNotNull().isGreaterThan(0);
        cardService.edit(card.getId(), CardStatusEditDto.builder().cardStatus(CardStatus.ACTIVE).build());
        return card;
    }


}

