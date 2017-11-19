package de.delinero.copt.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Coupon {

    @JsonProperty("type")
    private String type;

    @JsonProperty("discount")
    private Integer discount;

    @JsonProperty("rules")
    private final List<CouponRule> rules;

    @JsonProperty("expression")
    private String expression;

    public Coupon() {
        rules = new ArrayList<>();
    }

    public String getType() {
        return type;
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

    public void addRule(CouponRule rule) {
        rules.add(rule);
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}
