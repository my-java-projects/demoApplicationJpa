package org.example.demoapplication.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.demoapplication.dto.CustomerDTO;
import org.example.demoapplication.dto.DepositDTO;
import org.example.demoapplication.dto.DepositTypeDTO;
import org.example.demoapplication.dto.WithdrawalDocumentDTO;
import org.example.demoapplication.entity.*;
import org.example.demoapplication.exception.InsufficientFundsException;
import org.example.demoapplication.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class WithdrawalDocumentService {


    private DepositRepository depositRepository;
    private WithdrawalDocumentRepository withdrawalDocumentRepository;
    private ActiveMQService activeMQService;

    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public WithdrawalDocumentService(DepositRepository depositRepository,WithdrawalDocumentRepository withdrawalDocumentRepository,ActiveMQService activeMQService) {
        this.depositRepository = depositRepository;
        this.withdrawalDocumentRepository = withdrawalDocumentRepository;
        this.activeMQService = activeMQService;

    }

    @Transactional
    public WithdrawalDocumentDTO createWithdrawal(String depositNumber, BigDecimal withdrawalAmount, String description) throws InsufficientFundsException {
        Optional<Deposit> depositOptional = depositRepository.findByDepositNumber(depositNumber);

        if (depositOptional.isEmpty()) {
            throw new EntityNotFoundException("Deposit account not found.");
        }

        Deposit deposit = depositOptional.get();

        if (deposit.getWithdrawableBalance().compareTo(withdrawalAmount) < 0) {
            throw new InsufficientFundsException("Insufficient balance in the deposit.");
        }

        // Update the deposit balance
        deposit.setWithdrawableBalance(deposit.getWithdrawableBalance().subtract(withdrawalAmount));
        depositRepository.save(deposit);

        // Create the withdrawal document
        WithdrawalDocument document = new WithdrawalDocument();
        document.setDeposit(deposit);
        document.setWithdrawalAmount(withdrawalAmount);
        document.setDescription(description);
        document.setDocumentDate(LocalDateTime.now());
        document.setDocumentNumber(generateUniqueDocumentNumber());
        document.setSuccess(true);
        withdrawalDocumentRepository.save(document);

        // Send the SMS/notification using ActiveMQ
        activeMQService.sendWithdrawalNotification(deposit.getCustomer().getFirstName() ,deposit.getCustomer().getLastName() , withdrawalAmount);



        // Manually map the entity to the DTO
        WithdrawalDocumentDTO withdrawalDocumentDTO = new WithdrawalDocumentDTO();
        withdrawalDocumentDTO.setId(document.getId());
        withdrawalDocumentDTO.setDocumentDate(document.getDocumentDate());
        withdrawalDocumentDTO.setWithdrawalAmount(document.getWithdrawalAmount());
        withdrawalDocumentDTO.setDescription(document.getDescription());
        withdrawalDocumentDTO.setDocumentNumber(document.getDocumentNumber());
        withdrawalDocumentDTO.setSuccess(true);


        // Manually map the Deposit to DepositDTO
        DepositDTO depositDTO = new DepositDTO();
        depositDTO.setDepositNumber(deposit.getDepositNumber());
        depositDTO.setInitialBalance(deposit.getInitialBalance());
        depositDTO.setWithdrawableBalance(deposit.getWithdrawableBalance());

        // Map the Customer entity to CustomerDTO
        Customer customer = deposit.getCustomer();
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(customer.getFirstName());
        customerDTO.setLastName(customer.getLastName());
        customerDTO.setNationalId(customer.getNationalId());

        // Map the DepositType entity to DepositTypeDTO
        DepositType depositType = deposit.getDepositType();
        DepositTypeDTO depositTypeDTO = new DepositTypeDTO();
        depositTypeDTO.setTitle(depositType.getTitle());
        depositTypeDTO.setCode(depositType.getCode());

        // Set the nested DTOs into DepositDTO
        depositDTO.setCustomerDTO(customerDTO);
        depositDTO.setDepositTypeDTO(depositTypeDTO);


        // Set DepositDTO in WithdrawalDocumentDTO
        withdrawalDocumentDTO.setDepositDTO(depositDTO);
        return withdrawalDocumentDTO;
    }


    public Optional<WithdrawalDocumentDTO> getWithdrawalDocument(String documentNumber){
        return withdrawalDocumentRepository.findByDocumentNumber(documentNumber)
                .map(document -> modelMapper.map(document, WithdrawalDocumentDTO.class));
    }


    // Generate a unique document number
    private String generateUniqueDocumentNumber() {
        Random random = new Random();
        int uniqueNumber = random.nextInt(900000000) + 100000000; // Generates a code between 100000000 and 999999999
        while (withdrawalDocumentRepository.findByDocumentNumber(String.valueOf(uniqueNumber)).isPresent()) {
            uniqueNumber = random.nextInt(900000000) + 100000000; // Ensure the code is unique
        }
        return String.valueOf(uniqueNumber);
    }

}
