package com.revature.RevPay.controllers;

import com.revature.RevPay.dtos.TransDto;
import com.revature.RevPay.dtos.TransHistoryDto;
import com.revature.RevPay.entities.Transaction;
import com.revature.RevPay.entities.User;
import com.revature.RevPay.exceptions.InsufficientBalanceException;
import com.revature.RevPay.exceptions.UserAlreadyExistsException;
import com.revature.RevPay.exceptions.UserNotFoundException;
import com.revature.RevPay.services.TransactionService;
import com.revature.RevPay.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@CrossOrigin(origins = {"http://localhost:4200"}, allowCredentials = "true")
@RestController
public class TransactionController {

    private final UserService userService;
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(UserService userService, TransactionService transactionService) {
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @PostMapping(path = "/transaction/{username}")
    @ResponseStatus(HttpStatus.OK)
    public Transaction sendMoney(@PathVariable String username, @RequestBody TransDto transDto) {
        return transactionService.sendMoney(username, transDto);
    }

    @GetMapping(path = "/transaction/{username}")
    @ResponseStatus(HttpStatus.OK)
    public Set<TransHistoryDto> getTransactionHistory(@PathVariable String username) {
        return transactionService.getTransactionHistory(username);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> internalErrorHandler(UserNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> internalErrorHandler(InsufficientBalanceException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
