package org.example.demoapplication.service;

import lombok.NoArgsConstructor;
import org.example.demoapplication.dto.CustomerDTO;
import org.example.demoapplication.entity.Customer;
import org.example.demoapplication.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
@NoArgsConstructor
public class CustomerService {

    private CustomerRepository customerRepository;

    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        customerDTO.setCustomerNumber(generateUniqueCustomerNumber());
        customerRepository.save(modelMapper.map(customerDTO, Customer.class));
        return customerDTO;
    }


    // Generate a unique customer number
    private String generateUniqueCustomerNumber() {
        Random random = new Random();
        int uniqueNumber = random.nextInt(900000000) + 100000000; // Generates a code between 100000000 and 999999999
        while (customerRepository.findByCustomerNumber(String.valueOf(uniqueNumber)).isPresent()) {
            uniqueNumber = random.nextInt(900000000) + 100000000; // Ensure the code is unique
        }
        return String.valueOf(uniqueNumber);
    }


    public Optional<CustomerDTO> findByNationalId(String nationalId) {
        return customerRepository.findByNationalId(nationalId)
                .map(customer -> modelMapper.map(customer, CustomerDTO.class));
    }


    public Optional<CustomerDTO> findByCustomerNumber(String customerNumber) {
        return customerRepository.findByCustomerNumber(customerNumber)
                .map(customer -> modelMapper.map(customer, CustomerDTO.class));
    }

}
