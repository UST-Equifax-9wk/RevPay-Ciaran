package com.revature.RevPay.repositories;

import com.revature.RevPay.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);

    @Modifying
    @Query(value = "UPDATE users SET balance = balance + :balance WHERE user_id = :user_id",
            nativeQuery = true)
    void updateBalance(@Param("user_id") Integer userId, @Param("balance") Double balance);
}
