package de.delinero.copt.models;

public class EvaluatedResult {

    private final String ruleName;
    private Boolean result;

    public EvaluatedResult(String ruleName, Boolean result) {
        this.ruleName = ruleName;
        this.result = result;
    }

    public String getRuleName() {
        return ruleName;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }
}
