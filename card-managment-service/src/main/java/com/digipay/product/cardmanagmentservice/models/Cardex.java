package com.digipay.product.cardmanagmentservice.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "cardex")
@SuperBuilder
public class Cardex extends ParentEntity {
    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_card_id", foreignKey = @ForeignKey(name = "fk_source_of_transfer"), nullable = false)
    private Card sourceCard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dist_card_id", foreignKey = @ForeignKey(name = "fk_dist_of_transfer"), nullable = false)
    private Card distCard;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "transfer_status", nullable = false)
    private TransferStatus transferStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "cardex_type", nullable = false)
    private CardexType cardexType;

    public Cardex() {
    }
}
