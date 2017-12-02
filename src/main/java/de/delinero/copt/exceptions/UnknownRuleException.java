package de.delinero.copt.exceptions;

public class UnknownRuleException extends Exception {

    private final String ruleName;

    public UnknownRuleException(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleName() {
        return ruleName;
    }
}
