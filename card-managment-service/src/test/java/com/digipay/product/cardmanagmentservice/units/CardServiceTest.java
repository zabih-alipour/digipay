package com.digipay.product.cardmanagmentservice.units;

import com.digipay.product.cardmanagmentservice.configs.TestAppConfig;
import com.digipay.product.cardmanagmentservice.dtos.CardDto;
import com.digipay.product.cardmanagmentservice.dtos.NameEditDto;
import com.digipay.product.cardmanagmentservice.dtos.SearchDto;
import com.digipay.product.cardmanagmentservice.exceptions.InvalidInputException;
import com.digipay.product.cardmanagmentservice.models.Card;
import com.digipay.product.cardmanagmentservice.models.User;
import com.digipay.product.cardmanagmentservice.repositories.SearchCriteria;
import com.digipay.product.cardmanagmentservice.repositories.UserRepository;
import com.digipay.product.cardmanagmentservice.setvices.CardService;
import com.digipay.product.cardmanagmentservice.utils.DataUtil;
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
import org.springframework.test.context.transaction.TestTransaction;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
class CardServiceTest {
    @Autowired
    private CardService cardService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DataUtil dataUtil;
    private final String defaultDatabaseUsername = "ali";

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
                .number(dataUtil.randomCardNumber())
                .build();
        cardService.add(cardDto_1);
        //-- card 2
        CardDto cardDto_2 = CardDto.builder()
                .user(user)
                .number(dataUtil.randomCardNumber())
                .build();
        cardService.add(cardDto_2);

    }

    @Test
    void add___normal_card___success() {
        User user = userRepository.findByUsername(defaultDatabaseUsername);
        CardDto cardDto = CardDto.builder()
                .user(user)
                .number(dataUtil.randomCardNumber())
                .build();
        Card card = cardService.add(cardDto);

        assertThat(card.getId()).isNotNull().isGreaterThan(0);
        assertThat(card.getNumber()).isEqualTo(cardDto.getNumber());
        assertThat(card.getUser().getId()).isEqualTo(cardDto.getUser().getId());
    }

    @Test
    void add___user_is_null___fail() {
        CardDto cardDto = CardDto.builder()
                .number(dataUtil.randomCardNumber())
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
    void add___number_isNull___fail() {
        User user = userRepository.findByUsername(defaultDatabaseUsername);
        CardDto cardDto = CardDto.builder()
                .name("کارت خودمه")
                .user(user)
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
    void add___number_has_none_numeric_character___fail() {
        User user = userRepository.findByUsername(defaultDatabaseUsername);
        CardDto cardDto = CardDto.builder()
                .user(user)
                .number("6037we9940_4512_4563")
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
    void edit___change_name___success() throws Exception {
        User user = userRepository.findByUsername(defaultDatabaseUsername);
        CardDto cardDto = CardDto.builder()
                .user(user)
                .name("کارت احمد برق کش")
                .number(dataUtil.randomCardNumber())
                .build();
        Card card = cardService.add(cardDto);
        assertThat(card.getId()).isNotNull().isGreaterThan(0);

        NameEditDto dto = NameEditDto.builder()
                .name("کارت صغرا جوراب باف")
                .build();
        Card editedCard = cardService.edit(card.getId(), dto);
        assertThat(editedCard.getName()).isNotEqualTo(cardDto.getName());

    }

    @Test
    void delete___unused_card___success() throws Exception {
        Card card = dataUtil.commonCardForTests();
        int cardSizeBeforeDelete = cardService.getAll().size();

        cardService.delete(card.getId());
        assertThat(cardService.getAll().size()).isLessThan(cardSizeBeforeDelete).isEqualTo(cardSizeBeforeDelete - 1);
    }



    @Test
    void findPaginated___search_by_name___success() {
        userRepository.findAll().forEach(user -> {
            CardDto cardDto1 = CardDto.builder()
                    .user(user)
                    .number(dataUtil.randomCardNumber())
                    .build();
            Card card1 = cardService.add(cardDto1);
            assertThat(card1.getId()).isNotNull().isGreaterThan(0);
        });

        SearchDto searchDto = new SearchDto();
        searchDto.setPageSize(2);
        searchDto.setSearchCriteria(Collections.singletonList(
                SearchCriteria.builder()
                        .key("name")
                        .operation(SearchCriteria.Operation.CONTAIN)
                        .value("6037").build()));
        Page<Card> paginated = cardService.find(searchDto);
        assertThat(paginated.getTotalElements()).isGreaterThan(0);
        paginated.iterator().forEachRemaining(p -> assertThat(p.getName().contains("6037")).isTrue());
    }

    @Test
    void add___number_has_more_than_16_digit___fail() {
        User user = userRepository.findByUsername(defaultDatabaseUsername);
        CardDto cardDto = CardDto.builder()
                .user(user)
                .number("6037we9940_4512_4563542")
                .build();

        assertThatExceptionOfType(InvalidInputException.class)
                .isThrownBy(() -> {
                    cardService.add(cardDto);
                })
                .withMessageContaining("مقدار فیلد(های)")
                .withMessageContaining("شماره کارت")
                .withMessageContaining("اشتباه است");
    }
}

