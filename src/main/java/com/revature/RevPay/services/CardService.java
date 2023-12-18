package com.revature.RevPay.services;

import com.revature.RevPay.repositories.CardRepository;
import com.revature.RevPay.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional(Transactional.TxType.REQUIRED)
public class CardService {

    private final UserRepository userRepository;
    private final CardRepository cardRepository;

    @Autowired
    public CardService(UserRepository userRepository, CardRepository cardRepository) {
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
    }

}
