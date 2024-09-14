package org.example.demoapplication.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Data // Lombok will generate getters, setters, toString, equals, and hashcode
@NoArgsConstructor // Lombok will generate a no-arg constructor
@Table(name = "NGJ_WITHDRAWALDOCUMENT")
public class WithdrawalDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deposit_id", nullable = false)
    private Deposit deposit;   // The related deposit

    @Column(unique = true, nullable = false)
    private String documentNumber;   // Unique document number

    private BigDecimal withdrawalAmount; // Amount withdrawn from the deposit
    private String description;
    private LocalDateTime documentDate;
    private boolean success; // To indicate success or failure of the operation

    public WithdrawalDocument(Deposit deposit, String documentNumber, BigDecimal withdrawalAmount, String description, LocalDate documentDate, boolean success) {

        this.deposit = deposit;
        this.documentNumber = documentNumber;
        this.withdrawalAmount = withdrawalAmount;
        this.description = description;
        this.documentDate = LocalDateTime.now();
        this.success = success;
    }
}
