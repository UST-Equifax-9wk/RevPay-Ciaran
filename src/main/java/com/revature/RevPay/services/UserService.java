package com.revature.RevPay.services;

import com.revature.RevPay.repositories.CardRepository;
import com.revature.RevPay.repositories.TransactionRepository;
import com.revature.RevPay.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional(Transactional.TxType.REQUIRED)
public class UserService {

    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public UserService(UserRepository userRepository, CardRepository cardRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
        this.transactionRepository = transactionRepository;
    }
}
