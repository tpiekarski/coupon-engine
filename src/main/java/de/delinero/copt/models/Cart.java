package de.delinero.copt.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Cart {

    @JsonProperty("value")
    private Integer value;

    @JsonProperty("items")
    private List<CartItem> items;

    public Integer getValue() {
        return value;
    }

    public List<CartItem> getItems() {
        return items;
    }
}
