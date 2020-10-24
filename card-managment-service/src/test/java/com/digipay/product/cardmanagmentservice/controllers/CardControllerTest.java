package com.digipay.product.cardmanagmentservice.controllers;

import com.digipay.product.cardmanagmentservice.configs.TestAppConfig;
import com.digipay.product.cardmanagmentservice.dtos.CardDto;
import com.digipay.product.cardmanagmentservice.dtos.NameEditDto;
import com.digipay.product.cardmanagmentservice.models.Card;
import com.digipay.product.cardmanagmentservice.models.User;
import com.digipay.product.cardmanagmentservice.repositories.UserRepository;
import com.digipay.product.cardmanagmentservice.utils.DataUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@ComponentScan(basePackages = "com.digipay.product.cardmanagmentservice")
@ActiveProfiles("test")
@Import(TestAppConfig.class)
@Sql(scripts = "classpath:downs.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CardControllerTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private DataUtil dataUtil;

    public static final String WEB_URL = "http://localhost:8082";
    HttpHeaders headers = new HttpHeaders();

    @Test
    public void add___normal_object___success() {
        User user = User.builder()
                .name("kobra")
                .username("kobi")
                .password("kkhs98as")
                .mobile("09025845638")
                .build();
        userRepository.save(user);

        CardDto cardDto_1 = CardDto.builder()
                .user(user)
                .number(dataUtil.randomCardNumber())
                .build();

        HttpEntity<CardDto> entity = new HttpEntity<>(cardDto_1, headers);
        ResponseEntity<Card> exchange = restTemplate.exchange(WEB_URL.concat("/api/card_management/cards"),
                HttpMethod.POST, entity, Card.class);
        Card card = exchange.getBody();
        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(card.getNumber()).isEqualTo(cardDto_1.getNumber());

    }

    @Test
    public void edit___change_name___success() {
        Card card = dataUtil.commonCardForTests();

        NameEditDto editDto = NameEditDto.builder()
                .name("کارت مال اکبر بنا هستش")
                .build();

        HttpEntity<NameEditDto> entity = new HttpEntity<>(editDto, headers);
        ResponseEntity<Card> exchange = restTemplate.exchange(WEB_URL.concat("/api/card_management/cards/" + card.getId()),
                HttpMethod.PUT, entity, Card.class);
        Card editedCard = exchange.getBody();
        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(editedCard.getName()).isNotEqualTo(card.getName());
    }

    @Test
    public void get___normal_card___success() {
        Card card = dataUtil.commonCardForTests();

        HttpEntity<Card> entity = new HttpEntity<>(null, headers);
        ResponseEntity<Card> exchange = restTemplate.exchange(WEB_URL.concat("/api/card_management/cards/" + card.getId()),
                HttpMethod.GET, entity, Card.class);
        Card result = exchange.getBody();
        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    public void delete___normal_card___success() {
        Card card = dataUtil.commonCardForTests();

        HttpEntity<NameEditDto> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> exchange = restTemplate.exchange(WEB_URL.concat("/api/card_management/cards/" + card.getId()),
                HttpMethod.DELETE, entity, String.class);
        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(exchange.getBody()).isEqualTo("Card deleted");

    }
}
