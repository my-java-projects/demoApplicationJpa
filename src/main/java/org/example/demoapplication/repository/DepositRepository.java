package org.example.demoapplication.repository;

import org.example.demoapplication.entity.Customer;
import org.example.demoapplication.entity.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepositRepository extends JpaRepository<Deposit, Long> {
    List<Deposit> findByCustomer(Optional<Customer> customer);
    //List<Deposit> findByCustomerNumber(String CustomerNumber);
}