package com.digipay.product.cardmanagmentservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "card", uniqueConstraints = {
        @UniqueConstraint(name = "uq_card_number_of_card", columnNames = "card_number"),
        @UniqueConstraint(name = "uq_user__card_of_card", columnNames = {"user_id", "card_number"}),
})
@SuperBuilder
public class Card extends ParentEntity {
    @Column(name = "card_number", updatable = false, nullable = false, length = 16)
    private String number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_of_card"), nullable = false)
    private User user;

    @Column(name = "vcc2", updatable = false, nullable = false)
    private Long vcc2;

    @Column(name = "expiration_date", nullable = false)
//    @JsonSerialize()                              // TODO: 10/23/20 This field should serialize with a custom Serializer: for support Jalali date conversion
//    @JsonDeserialize()                            // TODO: 10/23/20 This field should serialize with a custom DeSerializer: for support Jalali date conversion
    private LocalDate expirationDate;

    @JsonIgnore
    @Column(name = "online_pass", nullable = false)
    private String onlinePass;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_status", nullable = false)
    private CardStatus cardStatus;

    @CreationTimestamp
    private LocalDateTime createDate;

    @UpdateTimestamp
    private LocalDateTime updateDate;

    public Card() {
    }

    @PrePersist
    public void prePersist() {
        this.setCardStatus(CardStatus.ACTIVE);
        if (this.onlinePass != null) {
            this.onlinePass = BCrypt.hashpw(onlinePass, BCrypt.gensalt(11));
        }
    }
}
