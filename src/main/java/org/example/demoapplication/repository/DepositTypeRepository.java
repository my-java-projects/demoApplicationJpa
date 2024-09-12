package org.example.demoapplication.repository;

import org.example.demoapplication.entity.DepositType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepositTypeRepository extends JpaRepository<DepositType, Long> {
    boolean existsByCode(Integer code);
    Optional<DepositType> findByCode(Integer code);
}