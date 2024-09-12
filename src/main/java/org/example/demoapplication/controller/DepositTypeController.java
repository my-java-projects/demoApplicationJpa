package org.example.demoapplication.controller;

import lombok.NoArgsConstructor;
import org.example.demoapplication.dto.DepositTypeDTO;
import org.example.demoapplication.service.DepositTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/deposit-types")
@NoArgsConstructor
public class DepositTypeController {

    private DepositTypeService depositTypeService;

    @Autowired
    public DepositTypeController(DepositTypeService depositTypeService) {
        this.depositTypeService = depositTypeService;
    }

    @PostMapping
    public ResponseEntity<String> createDepositType(@RequestBody DepositTypeDTO depositTypeDTO) {
        DepositTypeDTO createdDepositType = depositTypeService.createDepositType(depositTypeDTO);
        return ResponseEntity.ok("Deposit Type created with Code: " + createdDepositType.getCode());
    }

    @GetMapping("/{code}")
    public ResponseEntity<DepositTypeDTO> getDepositTypeByCode(@PathVariable Integer code) {
        return depositTypeService.getDepositTypeByCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).body(null));
    }
}
