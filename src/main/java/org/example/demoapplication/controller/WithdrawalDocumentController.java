package org.example.demoapplication.controller;

import org.example.demoapplication.dto.CustomerDTO;
import org.example.demoapplication.dto.WithdrawalDocumentDTO;
import org.example.demoapplication.entity.WithdrawalDocument;
import org.example.demoapplication.exception.InsufficientFundsException;
import org.example.demoapplication.service.WithdrawalDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/withdrawals")
public class WithdrawalDocumentController {

    private WithdrawalDocumentService withdrawalService;

    @Autowired
    public WithdrawalDocumentController(WithdrawalDocumentService withdrawalService) {
        this.withdrawalService = withdrawalService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createWithdrawal(@RequestParam String depositNumber,
                                              @RequestParam BigDecimal withdrawalAmount,
                                              @RequestParam String description) {

        try {
            WithdrawalDocumentDTO document = withdrawalService.createWithdrawal(depositNumber, withdrawalAmount, description);
            return ResponseEntity.ok(Map.of("message", "Withdrawal successful", "document", document));
        } catch (InsufficientFundsException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to process withdrawal"));
        }
    }


    @GetMapping("/{documentNumber}")
    public ResponseEntity<WithdrawalDocumentDTO> getWithdrawalDocument(@PathVariable String documentNumber) {
        return withdrawalService.getWithdrawalDocument(documentNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).body(null));
    }
}
