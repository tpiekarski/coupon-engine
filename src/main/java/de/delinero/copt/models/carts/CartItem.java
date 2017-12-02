package de.delinero.copt.models.carts;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CartItem {

    @JsonProperty("sku")
    private String sku;

    @JsonProperty("category")
    private String category;

    @JsonProperty("price")
    private Integer price;

    public String getSku() {
        return sku;
    }

    public String getCategory() {
        return category;
    }

    public Integer getPrice() {
        return price;
    }
}
