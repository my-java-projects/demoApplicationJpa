package org.example.demoapplication.repository;

import jakarta.persistence.LockModeType;
import org.example.demoapplication.entity.Customer;
import org.example.demoapplication.entity.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepositRepository extends JpaRepository<Deposit, Long> {
    List<Deposit> findByCustomer(Optional<Customer> customer);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Deposit> findByDepositNumber(String depositNumber);

}