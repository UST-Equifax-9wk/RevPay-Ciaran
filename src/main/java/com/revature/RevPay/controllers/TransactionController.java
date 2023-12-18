package com.revature.RevPay.controllers;

import com.revature.RevPay.services.TransactionService;
import com.revature.RevPay.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    private final UserService userService;
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(UserService userService, TransactionService transactionService) {
        this.userService = userService;
        this.transactionService = transactionService;
    }
}
