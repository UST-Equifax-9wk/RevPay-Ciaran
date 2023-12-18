package com.revature.RevPay.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @Column(name = "transaction_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transactionId;

    @ManyToOne
    @JoinColumn(name = "user_id_to", nullable = false)
    @JsonBackReference
    private User userIdTo;

    @ManyToOne
    @JoinColumn(name = "user_id_from", nullable = false)
    @JsonBackReference
    private User userIdFrom;

    @Column(name = "timestamp", nullable = false)
    private String timestamp;

    @Column(name = "amount")
    private Double amount;

    public Transaction() {
    }

    public Transaction(Integer transactionId, User userIdTo, User userIdFrom, String timestamp, Double amount) {
        this.transactionId = transactionId;
        this.userIdTo = userIdTo;
        this.userIdFrom = userIdFrom;
        this.timestamp = timestamp;
        this.amount = amount;
    }

    public Transaction(User userIdTo, User userIdFrom, String timestamp, Double amount) {
        this.userIdTo = userIdTo;
        this.userIdFrom = userIdFrom;
        this.timestamp = timestamp;
        this.amount = amount;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public User getUserIdTo() {
        return userIdTo;
    }

    public void setUserIdTo(User userIdTo) {
        this.userIdTo = userIdTo;
    }

    public User getUserIdFrom() {
        return userIdFrom;
    }

    public void setUserIdFrom(User userIdFrom) {
        this.userIdFrom = userIdFrom;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(transactionId, that.transactionId) && Objects.equals(userIdTo, that.userIdTo) && Objects.equals(userIdFrom, that.userIdFrom) && Objects.equals(timestamp, that.timestamp) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, userIdTo, userIdFrom, timestamp, amount);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", userIdTo=" + userIdTo +
                ", userIdFrom=" + userIdFrom +
                ", timestamp='" + timestamp + '\'' +
                ", amount=" + amount +
                '}';
    }
}
