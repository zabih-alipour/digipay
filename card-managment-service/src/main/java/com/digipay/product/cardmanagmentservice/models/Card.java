package com.digipay.product.cardmanagmentservice.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "card", uniqueConstraints = {
        @UniqueConstraint(name = "uq_card_number_of_card", columnNames = "card_number"),
        @UniqueConstraint(name = "uq_number__vcc2_of_card", columnNames = {"card_number", "vcc2"})
})
@SuperBuilder
public class Card extends ParentEntity {
    @Column(name = "card_number", updatable = false, nullable = false, length = 16)
    private String number;

    @Column(name = "owner_name", nullable = false)
    private String ownerName; // TODO: 10/21/20 This field should reference to user object. temporary implemented as String

    @Column(name = "vcc2", updatable = false, nullable = false)
    private String vcc2;

    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    @Column(name = "online_pass", nullable = false)
    private String onlinePass;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_status", nullable = false)
    private CardStatus cardStatus;

    @CreationTimestamp
    private LocalDateTime createDate;

    @UpdateTimestamp
    private LocalDateTime updateDate;

}
