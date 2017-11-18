package de.delinero.copt.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Optional;

public class Coupon {

    @JsonProperty("type")
    private String type;

    @JsonProperty("target")
    private String target;

    @JsonProperty("discount")
    private Integer discount;

    @JsonProperty("rules")
    private List<CouponRule> rules;

    public String getType() {
        return type;
    }

    public String getTarget() {
        return target;
    }

    public Integer getDiscount() {
        return discount;
    }

    public List<CouponRule> getRules() {
        return rules;
    }

    public Optional<CouponRule> getRuleByName(String name) {
        return rules.stream().filter((rule) -> (rule.getRuleName().equals(name))).findFirst();
    }
}
