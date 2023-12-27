package com.revature.RevPay.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum CardType {
    @JsonProperty("credit")
    CREDIT,
    @JsonProperty("debit")
    DEBIT
}
