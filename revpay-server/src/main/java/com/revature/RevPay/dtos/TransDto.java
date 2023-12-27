package com.revature.RevPay.dtos;

// object that represents request to transfer some amount to username or email or phone number
public class TransDto {

    private String identifier;
    private Double balance;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "TransDto{" +
                "identifier='" + identifier + '\'' +
                ", balance=" + balance +
                '}';
    }
}
