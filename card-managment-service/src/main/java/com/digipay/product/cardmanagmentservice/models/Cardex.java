package com.digipay.product.cardmanagmentservice.models;

import com.digipay.product.cardmanagmentservice.dtos.ReportDto;
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

@SqlResultSetMapping(
        name = "reportDetails",
        classes = {
                @ConstructorResult(
                        targetClass = ReportDto.class,
                        columns = {
                                @ColumnResult(name = "source_card", type = String.class),
                                @ColumnResult(name = "cntSuccess", type = Long.class),
                                @ColumnResult(name = "cntFail", type = Long.class),
                                @ColumnResult(name = "amount", type = Long.class)
                        }
                )
        }
)

@NamedNativeQuery(name = "Cardex.getReportDetails",
        query = "select inner_tb.source_card, " +
                "       sum(inner_tb.success) as cntSuccess, " +
                "       sum(inner_tb.fail) as cntFail, " +
                "       sum(inner_tb.amount) as amount" +
                "from ( " +
                "         select c.source_card, " +
                "                case when transfer_status = 'SUCCESS' then 1 else 0 end as success, " +
                "                case when transfer_status = 'FAIL' then 1 else 0 end    as fail, " +
                "                c.amount " +
                "         from cardex c " +
                "         where c.create_date >= :from " +
                "           and c.create_date <= :to) inner_tb " +
                "group by inner_tb.source_card", resultSetMapping = "reportDetails")
public class Cardex extends ParentEntity {
    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_of_cardex"), nullable = false)
    private User user;

    @Column(name = "source_card", nullable = false)
    private String sourceCard;

    @Column(name = "dest_card")
    private String destCard;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "transfer_status", nullable = false)
    private TransferStatus transferStatus;


    public Cardex() {
    }

    @PrePersist
    public void prePersist() {
        this.createDate = LocalDateTime.now();
    }
}
