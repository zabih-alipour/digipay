package com.digipay.product.cardmanagmentservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "card", uniqueConstraints = {
        @UniqueConstraint(name = "uq_user__card_of_card", columnNames = {"user_id", "card_number"}),
})
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Card extends ParentEntity {
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "card_number", nullable = false, length = 16)
    private String number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_of_card"), nullable = false)
    private User user;

    @CreationTimestamp
    @JsonIgnore
    private LocalDateTime createDate;

    @UpdateTimestamp
    @JsonIgnore
    private LocalDateTime updateDate;

    public Card() {
    }
}
