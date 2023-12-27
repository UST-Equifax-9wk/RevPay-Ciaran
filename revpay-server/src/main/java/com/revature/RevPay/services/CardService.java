package com.revature.RevPay.services;

import com.revature.RevPay.entities.Card;
import com.revature.RevPay.entities.User;
import com.revature.RevPay.exceptions.CardAlreadyExistsException;
import com.revature.RevPay.exceptions.UserNotFoundException;
import com.revature.RevPay.repositories.CardRepository;
import com.revature.RevPay.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

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

    public Set<Card> findCardsByUsername(String username) throws UserNotFoundException {
        Optional<User> userLookup = userRepository.findByUsername(username);
        if (userLookup.isPresent()) {
            return cardRepository.findAllByUserId(userLookup.get().getUserId());
        }
        throw new UserNotFoundException("No user with username [" + username + "] found!");
    }

    public Card registerCard (String username, Card card) {
        Optional<User> userLookup = userRepository.findByUsername(username);
        if (userLookup.isPresent()) {
            Optional<Card> cardLookup =
                    cardRepository.findByCardNumberAndUserId(userLookup.get().getUserId(), card.getCardNumber());
            if (cardLookup.isPresent()) {
                throw new CardAlreadyExistsException("Card already exists for user!");
            }
            card.setUserId(userLookup.get());
            // System.out.println(card);
            return cardRepository.save(card);
        }
        throw new UserNotFoundException("No user with username [" + username + "] found!");
    }
}
