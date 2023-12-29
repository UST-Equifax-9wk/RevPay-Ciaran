package com.revature.RevPay.services;

import com.revature.RevPay.dtos.LoginDto;
import com.revature.RevPay.entities.User;
import com.revature.RevPay.exceptions.UserAlreadyExistsException;
import com.revature.RevPay.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    // use UserRepository info in place of AuthRepository
    private final UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        return associatedPass.equals(loginDto.getPassword());
    }

}
