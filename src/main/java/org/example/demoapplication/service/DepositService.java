package org.example.demoapplication.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.NoArgsConstructor;
import org.example.demoapplication.dto.DepositDTO;
import org.example.demoapplication.entity.*;
import org.example.demoapplication.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
public class DepositService {

    private DepositRepository depositRepository;
    private CustomerRepository customerRepository;
    private DepositTypeRepository depositTypeRepository;

    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public DepositService(DepositRepository depositRepository, CustomerRepository customerRepository, DepositTypeRepository depositTypeRepository) {
        this.depositRepository = depositRepository;
        this.customerRepository = customerRepository;
        this.depositTypeRepository = depositTypeRepository;
    }

    public DepositDTO createDeposit(String customerNumber, Integer depositTypeCode, BigDecimal initialBalance) {
        Optional<Customer> customerOptional = customerRepository.findByCustomerNumber(customerNumber);
        if (customerOptional.isEmpty()) {
            throw new EntityNotFoundException("Customer not found");
        }

        Optional<DepositType> depositTypeOptional = depositTypeRepository.findByCode(depositTypeCode);
        if (depositTypeOptional.isEmpty()) {
            throw new EntityNotFoundException("Deposit Type not found");
        }

        Customer customer = customerOptional.get();
        DepositType depositType = depositTypeOptional.get();
        Deposit deposit = new Deposit(customer, depositType, initialBalance);
        depositRepository.save(deposit);

        return modelMapper.map(deposit, DepositDTO.class);
    }

    public List<DepositDTO> getDepositsByCustomerNumber(String customerNumber) {
        Optional<Customer> customer = customerRepository.findByCustomerNumber(customerNumber);
        List<Deposit> deposits = depositRepository.findByCustomer(customer);
        // Convert entities to DTOs
        return deposits.stream()
                .map(deposit -> modelMapper.map(deposit, DepositDTO.class))
                .collect(Collectors.toList());
    }
}
