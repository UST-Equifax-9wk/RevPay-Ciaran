package com.revature.RevPay.repositories;

import com.revature.RevPay.entities.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {

    // for some reason JPQL syntax doesn't behave so native SQL it is for now
    @Query(value = "SELECT * FROM cards c WHERE c.user_id = :user_id", nativeQuery = true)
    Set<Card> findAllByUserId(@Param("user_id") Integer userId);

    @Query(value = "SELECT * FROM cards c WHERE c.user_id = :user_id and c.card_number = :card_number", nativeQuery = true)
    Optional<Card> findByCardNumberAndUserId(
            @Param("user_id") Integer userId, @Param("card_number") String cardNumber);
}
