package com.revature.RevPay.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "account_type", nullable = false)
    private AccountType accountType;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Card> cards;

    // transactions where the user sent money to another account
    @OneToMany(mappedBy = "userIdFrom", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Transaction> payments;

    // transactions where the user received money from another account
    @OneToMany(mappedBy = "userIdTo", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Transaction> receipts;

    public User() {
    }

    public User(Integer userId, AccountType accountType, String username, String password, String email, String phoneNumber, Set<Card> cards, Set<Transaction> payments, Set<Transaction> receipts) {
        this.userId = userId;
        this.accountType = accountType;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.cards = cards;
        this.payments = payments;
        this.receipts = receipts;
    }

    public User(AccountType accountType, String username, String password, String email, String phoneNumber, Set<Card> cards, Set<Transaction> payments, Set<Transaction> receipts) {
        this.accountType = accountType;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.cards = cards;
        this.payments = payments;
        this.receipts = receipts;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) && accountType == user.accountType && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(email, user.email) && Objects.equals(phoneNumber, user.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, accountType, username, password, email, phoneNumber);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", accountType=" + accountType +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
