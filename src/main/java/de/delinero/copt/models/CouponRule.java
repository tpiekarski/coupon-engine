package de.delinero.copt.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CouponRule {

    @JsonProperty("rule")
    private String rule;

    @JsonProperty("option")
    private String option;

    public String getRuleName() {
        return rule;
    }

    public String getOption() {
        return option;
    }
}
