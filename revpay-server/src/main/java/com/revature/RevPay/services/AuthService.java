package com.revature.RevPay.services;

import com.revature.RevPay.dtos.LoginDto;
import com.revature.RevPay.entities.User;
import com.revature.RevPay.exceptions.AccessDeniedException;
import com.revature.RevPay.exceptions.UserAlreadyExistsException;
import com.revature.RevPay.exceptions.UserNotFoundException;
import com.revature.RevPay.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private static final int saltSize = 12;
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
        user.setPassword(this.hash(user.getPassword()));
        // System.out.println(user.getPassword());
        return userRepository.save(user);
    }

    public boolean login(LoginDto loginDto) { // checks if provided pass equals pass associated with user
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
        if (associatedPass.isEmpty()) {
            throw new UserNotFoundException("User not found!");
        }
        // System.out.println(loginDto.getPassword());
        // System.out.println(associatedPass);
        return this.checkHash(loginDto.getPassword(), associatedPass);
        // System.out.println(isValid);
    }

    // cross-references the stored cookie with the hash of the username of the user whose page is
    // attempting to be accessed
    public User authenticate(String username, String hash) {
        if (checkHash(username, hash)) {
            Optional<User> usernameLookup = userRepository.findByUsername(username);
            if (usernameLookup.isPresent()) {
                return usernameLookup.get();
            }
            System.out.println("AuthService.authenticate: User not found");
            throw new UserNotFoundException("User doesn't exist!");
        }
        System.out.println("AuthService.authenticate: Cookies don't match");
        throw new AccessDeniedException("Invalid cookie!");
    }

    public String hash(String text) {
        String salt = BCrypt.gensalt(saltSize);
        return BCrypt.hashpw(text, salt);
    }

    // want to cross-reference the stored cookie with the hash of the username of the user whose
    // page is attempting to be accessed
    public boolean checkHash(String text, String hash) {
        return BCrypt.checkpw(text, hash);
    }
}
