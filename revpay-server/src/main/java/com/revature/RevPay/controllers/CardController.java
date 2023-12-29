package com.revature.RevPay.controllers;

import com.revature.RevPay.entities.Card;
import com.revature.RevPay.entities.User;
import com.revature.RevPay.exceptions.CardAlreadyExistsException;
import com.revature.RevPay.exceptions.UserAlreadyExistsException;
import com.revature.RevPay.exceptions.UserNotFoundException;
import com.revature.RevPay.services.CardService;
import com.revature.RevPay.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@CrossOrigin(origins = {"http://localhost:4200"}, allowCredentials = "true")
@RestController
public class CardController {

    private final UserService userService;
    private final CardService cardService;

    @Autowired
    public CardController(UserService userService, CardService cardService) {
        this.userService = userService;
        this.cardService = cardService;
    }

    @GetMapping(path = "/user/{username}/card")
    @ResponseStatus(HttpStatus.OK)
    public Set<Card> findCardsByUsername(@PathVariable String username) {
        return cardService.findCardsByUsername(username);
    }

    @PostMapping(path = "/user/{username}/card")
    @ResponseStatus(HttpStatus.OK)
    public Card registerCard(@PathVariable String username, @RequestBody Card card) {
        return cardService.registerCard(username, card);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> internalErrorHandler(UserNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CardAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> internalErrorHandler(CardAlreadyExistsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
