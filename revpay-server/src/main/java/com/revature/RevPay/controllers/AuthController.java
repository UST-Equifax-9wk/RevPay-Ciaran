package com.revature.RevPay.controllers;

import com.revature.RevPay.dtos.LoginDto;
import com.revature.RevPay.entities.User;
import com.revature.RevPay.exceptions.AccessDeniedException;
import com.revature.RevPay.exceptions.UserAlreadyExistsException;
import com.revature.RevPay.exceptions.UserNotFoundException;
import com.revature.RevPay.services.AuthService;
import com.revature.RevPay.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:4200"}, allowCredentials = "true")
@RestController
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @Autowired
    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping(path = "/user")
    @ResponseStatus(HttpStatus.OK)
    public User registerUser(@RequestBody User user) {
        return authService.registerUser(user);
    }

    @PostMapping(path = "/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public boolean login(@RequestBody LoginDto loginDto) {
        boolean login = authService.login(loginDto);
        if (login) {
            // establish cookie or something
            return true;
        }
        throw new AccessDeniedException("Credentials do not match!");
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> internalErrorHandler(UserNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> internalErrorHandler(UserAlreadyExistsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> internalErrorHandler(AccessDeniedException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
