package com.revature.RevPay.controllers;

import com.revature.RevPay.dtos.LoginDto;
import com.revature.RevPay.entities.User;
import com.revature.RevPay.exceptions.AccessDeniedException;
import com.revature.RevPay.exceptions.UserAlreadyExistsException;
import com.revature.RevPay.exceptions.UserNotFoundException;
import com.revature.RevPay.services.AuthService;
import com.revature.RevPay.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
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
    public User login(@RequestBody LoginDto loginDto, HttpServletResponse httpResponse) {
        if (authService.login(loginDto)) {
            try {
                User userLookup = userService.findByIdentifier(loginDto.getIdentifier());
                Cookie cookie = new Cookie("value", authService.hash(userLookup.getUsername()));
                cookie.setMaxAge(60 * 60 * 24 * 7); // one week
                cookie.setPath("/");
                httpResponse.addCookie(cookie);
                return userLookup;
            } catch (UserNotFoundException e) {
                throw new AccessDeniedException("Credentials do not match!");
            }
        }
        throw new AccessDeniedException("Credentials do not match!");
    }

    @GetMapping(path = "/cookie-test/{username}")
    @ResponseStatus(HttpStatus.OK)
    public User testCookie(@PathVariable(name = "username") String username,
                           @CookieValue(name = "value") String cookieValue) { //get cookie from request
        return authService.authenticate(username, cookieValue);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> internalErrorHandler(UserNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> internalErrorHandler(UserAlreadyExistsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<String> internalErrorHandler(AccessDeniedException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

}
