package com.revature.RevPay.repositories;

import com.revature.RevPay.dtos.TransHistoryDto;
import com.revature.RevPay.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Query(value = "SELECT * FROM transactions t " +
            "WHERE t.user_id_to = :user_id OR t.user_id_from = :user_id", nativeQuery = true)
    Set<Transaction> findAllTransactionsWithUser(@Param("user_id") Integer userId);
}
