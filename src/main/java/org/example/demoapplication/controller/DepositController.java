package org.example.demoapplication.controller;


import lombok.NoArgsConstructor;
import org.example.demoapplication.dto.DepositDTO;
import org.example.demoapplication.service.DepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/deposits")
@NoArgsConstructor
public class DepositController {

    private DepositService depositService;

    @Autowired
    public DepositController(DepositService depositService) {
        this.depositService = depositService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createDeposit(
            @RequestParam String customerNumber,
            @RequestParam Integer depositTypeCode,
            @RequestParam BigDecimal initialBalance) {

        try {
            // Create a new deposit
            DepositDTO deposit = depositService.createDeposit(customerNumber, depositTypeCode, initialBalance);

            // Construct the response body
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Deposit successfully created");
            response.put("openingDate", deposit.getOpeningDate());
            response.put("withdrawableBalance", deposit.getWithdrawableBalance());
            response.put("depositOwner", deposit.getCustomerDTO().getFirstName()+ " " + deposit.getCustomerDTO().getLastName());

            // Return the response as JSON
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to create deposit: " + e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getDepositsByCustomerNumber(
            @RequestParam String customerNumber) {

        try {
            // Retrieve deposits by customer number
            List<DepositDTO> deposits = depositService.getDepositsByCustomerNumber(customerNumber);

            List<Map<String, Object>> response = deposits.stream()
                    .map(deposit -> {
                        Map<String, Object> depositData = new HashMap<>();
                        depositData.put("openingDate", deposit.getOpeningDate());
                        depositData.put("withdrawableBalance", deposit.getWithdrawableBalance());
                        //depositData.put("depositOwner", deposit.getCustomerDTO().getFirstName() + " " + deposit.getCustomerDTO().getLastName());
                        return depositData;
                    })
                    .collect(Collectors.toList());

            // Return the response as JSON
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(List.of(Map.of("error", "Failed to retrieve deposits: " + e.getMessage())));
        }
    }
}
