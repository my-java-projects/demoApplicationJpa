package org.example.demoapplication.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.demoapplication.entity.Deposit;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawalDocumentDTO implements Serializable {

    private Long id;
    private DepositDTO depositDTO;   // The related deposit
    private String documentNumber;   // Unique document number
    private BigDecimal withdrawalAmount;
    private String description;
    private LocalDateTime documentDate;
    private boolean success;

    public WithdrawalDocumentDTO(DepositDTO depositDTO, String documentNumber, BigDecimal withdrawalAmount, String description, LocalDate documentDate, boolean success) {
        this.depositDTO = depositDTO;
        this.documentNumber = documentNumber;
        this.withdrawalAmount = withdrawalAmount;
        this.description = description;
        this.documentDate = LocalDateTime.now();
        this.success = success;
    }
}
