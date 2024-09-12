package org.example.demoapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepositDTO {
    private Long id;
    private CustomerDTO customerDTO; // Reference to the customer
    private DepositTypeDTO depositTypeDTO; // Reference to the deposit type
    private BigDecimal initialBalance;
    private BigDecimal withdrawableBalance;
    private LocalDateTime openingDate;

    public DepositDTO(CustomerDTO customerDTO, DepositTypeDTO depositTypeDTO, BigDecimal initialBalance) {
        this.customerDTO = customerDTO;
        this.depositTypeDTO = depositTypeDTO;
        this.initialBalance = initialBalance;
        this.withdrawableBalance = initialBalance;
        this.openingDate = LocalDateTime.now();
    }
}
