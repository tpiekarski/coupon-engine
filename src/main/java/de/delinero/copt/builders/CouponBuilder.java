package de.delinero.copt.builders;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.delinero.copt.models.Coupon;

import javax.inject.Inject;
import java.io.IOException;

public class CouponBuilder {

    private ObjectMapper objectMapper;

    @Inject
    public CouponBuilder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Coupon build(String cart) {
        try {
            return objectMapper.readValue(cart, Coupon.class);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}
