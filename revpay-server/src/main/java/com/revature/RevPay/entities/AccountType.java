package com.revature.RevPay.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum AccountType {
    @JsonProperty("personal")
    PERSONAL,

    @JsonProperty("business")
    BUSINESS
}