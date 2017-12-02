package de.delinero.copt.builders;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.delinero.copt.exceptions.DeserializationException;
import de.delinero.copt.models.coupons.Coupon;

import javax.inject.Inject;
import java.io.IOException;

public class CouponBuilder {

    private final ObjectMapper objectMapper;

    @Inject
    public CouponBuilder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Coupon build(String cart) {
        try {
            return objectMapper.readValue(cart, Coupon.class);
        } catch (IOException exception) {
            throw new DeserializationException(exception);
        }
    }

}
