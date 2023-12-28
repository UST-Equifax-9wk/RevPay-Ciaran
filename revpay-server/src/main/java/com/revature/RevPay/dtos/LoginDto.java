package com.revature.RevPay.dtos;

public class LoginDto {
    private String identifier;
    private String password;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginDto{" +
                "identifier='" + identifier + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
