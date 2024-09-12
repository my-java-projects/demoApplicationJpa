package org.example.demoapplication.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "NGJ_DEPOSIT")
public class Deposit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "deposit_type_id", nullable = false)
    private DepositType depositType;

    private BigDecimal initialBalance;   // BigDecimal for monetary values
    private BigDecimal withdrawableBalance;
    private LocalDateTime openingDate;

    public Deposit(Customer customer, DepositType depositType, BigDecimal initialBalance) {
        this.customer = customer;
        this.depositType = depositType;
        this.initialBalance = initialBalance;
        this.withdrawableBalance = initialBalance;
        this.openingDate = LocalDateTime.now();
    }

}
