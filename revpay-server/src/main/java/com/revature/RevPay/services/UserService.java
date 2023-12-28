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

    public User registerUser(User user) throws UserAlreadyExistsException {
        Optional<User> usernameLookup = userRepository.findByUsername(user.getUsername());
        Optional<User> emailLookup = userRepository.findByEmail(user.getEmail());
        Optional<User> phoneLookup = userRepository.findByPhoneNumber(user.getPhoneNumber());
        if (usernameLookup.isPresent() || emailLookup.isPresent() || phoneLookup.isPresent()) {
            throw new UserAlreadyExistsException("User with credentials already exists!");
        }
        return userRepository.save(user);
    }
    public User findByUsername(String username) throws UserNotFoundException {
        Optional<User> out = userRepository.findByUsername(username);
        if (out.isPresent()) {
            return out.get();
        }
        throw new UserNotFoundException("No user with username [" + username + "] found!");
    }

    public boolean login(LoginDto loginDto) {
        // very niche issue where the same string exists for two separate users as two of the below types
        Optional<User> usernameLookup = userRepository.findByUsername(loginDto.getIdentifier());
        Optional<User> emailLookup = userRepository.findByEmail(loginDto.getIdentifier());
        Optional<User> phoneLookup = userRepository.findByPhoneNumber(loginDto.getIdentifier());
        String associatedPass = "";
        if (usernameLookup.isPresent()) {
            associatedPass = usernameLookup.get().getPassword();
        } else if (emailLookup.isPresent()) {
            associatedPass = emailLookup.get().getPassword();
        } else if (phoneLookup.isPresent()) {
            associatedPass = phoneLookup.get().getPassword();
        }
        System.out.println("Lookup: " + associatedPass);
        System.out.println("Inputted Pass: " + loginDto.getPassword());
        return associatedPass.equals(loginDto.getPassword());
    }
    
}
