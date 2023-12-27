package com.revature.RevPay.services;

import com.revature.RevPay.dtos.TransDto;
import com.revature.RevPay.entities.Transaction;
import com.revature.RevPay.entities.User;
import com.revature.RevPay.exceptions.InsufficientBalanceException;
import com.revature.RevPay.exceptions.UserNotFoundException;
import com.revature.RevPay.repositories.TransactionRepository;
import com.revature.RevPay.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
@Transactional(Transactional.TxType.REQUIRED)
public class TransactionService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(UserRepository userRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    // will check for proper balance format (2 decimal places) via front-end
    public Transaction sendMoney(String sender, TransDto transDto) {
        Optional<User> userLookup = userRepository.findByUsername(sender);
        // check if user exists
        if (userLookup.isPresent()) {
            // check if user has enough money for transaction
            if (transDto.getBalance() > userLookup.get().getBalance()) {
                throw new InsufficientBalanceException("Insufficient balance for this transaction!");
            }
            // check if identifier in DTO matches with any users
            Optional<User> usernameLookup = userRepository.findByUsername(transDto.getIdentifier());
            Optional<User> emailLookup = userRepository.findByEmail(transDto.getIdentifier());
            Optional<User> phoneLookup = userRepository.findByPhoneNumber(transDto.getIdentifier());
            User realUser = null;
            if (usernameLookup.isPresent()) {
                realUser = usernameLookup.get();
            } else if (emailLookup.isPresent()) {
                realUser = emailLookup.get();
            } else if (phoneLookup.isPresent()) {
                realUser = phoneLookup.get();
            }
            if (realUser != null) {
                return transactionRepository.save(
                        new Transaction(
                                realUser,
                                userLookup.get(),
                                (new Timestamp(System.currentTimeMillis())).toString(),
                                transDto.getBalance()));
            } else {
                throw new UserNotFoundException("Recipient is not a valid user!");
            }
        }
        throw new UserNotFoundException("Sender is not a valid user!");
    }
}
