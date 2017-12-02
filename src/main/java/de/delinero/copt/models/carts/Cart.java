package de.delinero.copt.models.carts;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Cart {

    @JsonProperty("value")
    private Integer value;

    @JsonProperty("code")
    private String code;

    @JsonProperty("items")
    private List<CartItem> items;

    public Integer getValue() {
        return value;
    }

    public String getCode() {
        return code;
    }

    public List<CartItem> getItems() {
        return items;
    }
}
