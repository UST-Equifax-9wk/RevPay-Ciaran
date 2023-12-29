package com.revature.RevPay.dtos;

import java.time.LocalDateTime;

// separate DTO for retrieving transaction history, with username, timestamp, and amount
public class TransHistoryDto {
    LocalDateTime timestamp;
    String username;
    Double amount;

    public TransHistoryDto(LocalDateTime timestamp, String username, Double amount) {
        this.timestamp = timestamp;
        this.username = username;
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
