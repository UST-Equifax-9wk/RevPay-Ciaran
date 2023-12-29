package com.revature.RevPay.services;

import com.revature.RevPay.dtos.LoginDto;
import com.revature.RevPay.entities.User;
import com.revature.RevPay.exceptions.AccessDeniedException;
import com.revature.RevPay.exceptions.UserAlreadyExistsException;
import com.revature.RevPay.exceptions.UserNotFoundException;
import com.revature.RevPay.repositories.CardRepository;
import com.revature.RevPay.repositories.TransactionRepository;
import com.revature.RevPay.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public User findByUsername(String username) throws UserNotFoundException {
        Optional<User> out = userRepository.findByUsername(username);
        if (out.isPresent()) {
            return out.get();
        }
        throw new UserNotFoundException("No user found!");
    }

    public User findByIdentifier(String identifier) throws UserNotFoundException {
        Optional<User> usernameLookup = userRepository.findByUsername(identifier);
        Optional<User> emailLookup = userRepository.findByEmail(identifier);
        Optional<User> phoneLookup = userRepository.findByPhoneNumber(identifier);
        User out = null;
        if (usernameLookup.isPresent()) {
            out = usernameLookup.get();
        } else if (emailLookup.isPresent()) {
            out = emailLookup.get();
        } else if (phoneLookup.isPresent()) {
            out = phoneLookup.get();
        }
        if (out == null) {
            throw new UserNotFoundException("User not found!");
        }
        return out;
    }
    
}
