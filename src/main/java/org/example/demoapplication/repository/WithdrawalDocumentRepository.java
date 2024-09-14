package org.example.demoapplication.repository;


import org.example.demoapplication.entity.Deposit;
import org.example.demoapplication.entity.WithdrawalDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WithdrawalDocumentRepository extends JpaRepository<WithdrawalDocument, Long>{
    Optional<WithdrawalDocument> findByDocumentNumber(String documentNumber);
}
