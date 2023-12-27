package com.revature.RevPay.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "cards")
public class Card {

    @Id
    @Column(name = "card_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cardId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference(value = "card_user")
    private User userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_type", nullable = false)
    private CardType cardType;

    @Column(name = "card_number", unique = true)
    private String cardNumber;

    @Column(name = "expire_date")
    private String expireDate;

    @Column(name = "security_code")
    private String securityCode;

    public Card() {
    }

    public Card(Integer cardId, User userId, CardType cardType, String cardNumber, String expireDate, String securityCode) {
        this.cardId = cardId;
        this.userId = userId;
        this.cardType = cardType;
        this.cardNumber = cardNumber;
        this.expireDate = expireDate;
        this.securityCode = securityCode;
    }

    public Card(User userId, CardType cardType, String cardNumber, String expireDate, String securityCode) {
        this.userId = userId;
        this.cardType = cardType;
        this.cardNumber = cardNumber;
        this.expireDate = expireDate;
        this.securityCode = securityCode;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(cardId, card.cardId) && Objects.equals(userId, card.userId) && cardType == card.cardType && Objects.equals(cardNumber, card.cardNumber) && Objects.equals(expireDate, card.expireDate) && Objects.equals(securityCode, card.securityCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardId, userId, cardType, cardNumber, expireDate, securityCode);
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardId=" + cardId +
                ", userId=" + userId +
                ", cardType=" + cardType +
                ", cardNumber='" + cardNumber + '\'' +
                ", expireDate='" + expireDate + '\'' +
                ", securityCode='" + securityCode + '\'' +
                '}';
    }
}
