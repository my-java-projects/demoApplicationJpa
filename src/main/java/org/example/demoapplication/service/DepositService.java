package org.example.demoapplication.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.NoArgsConstructor;
import org.example.demoapplication.dto.CustomerDTO;
import org.example.demoapplication.dto.DepositDTO;
import org.example.demoapplication.dto.DepositTypeDTO;
import org.example.demoapplication.entity.*;
import org.example.demoapplication.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class DepositService {

    private DepositRepository depositRepository;
    private CustomerRepository customerRepository;
    private DepositTypeRepository depositTypeRepository;
    //private final ModelMapper modelMapper;
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
        deposit.setDepositNumber(generateUniqueDepositNumber());
        depositRepository.save(deposit);

        // Manually map the entity to the DTO
        DepositDTO depositDTO = new DepositDTO();
        depositDTO.setId(deposit.getId());
        depositDTO.setCustomerDTO(new CustomerDTO(customer.getFirstName(), customer.getLastName()));
        depositDTO.setDepositTypeDTO(new DepositTypeDTO(depositType.getCode(), depositType.getTitle()));
        depositDTO.setInitialBalance(deposit.getInitialBalance());
        depositDTO.setWithdrawableBalance(deposit.getWithdrawableBalance());
        depositDTO.setOpeningDate(deposit.getOpeningDate());

        return depositDTO;
    }

    public List<DepositDTO> getDepositsByCustomerNumber(String customerNumber) {
        Optional<Customer> customer = customerRepository.findByCustomerNumber(customerNumber);
        List<Deposit> deposits = depositRepository.findByCustomer(customer);
        // Convert entities to DTOs
        return deposits.stream()
                .map(deposit -> modelMapper.map(deposit, DepositDTO.class))
                .collect(Collectors.toList());
    }

    // Generate a unique deposit number
    private String generateUniqueDepositNumber() {
        Random random = new Random();
        int uniqueNumber = random.nextInt(900000000) + 100000000; // Generates a code between 100000000 and 999999999
        while (depositRepository.findByDepositNumber(String.valueOf(uniqueNumber)).isPresent()) {
            uniqueNumber = random.nextInt(900000000) + 100000000; // Ensure the code is unique
        }
        return String.valueOf(uniqueNumber);
    }

}
