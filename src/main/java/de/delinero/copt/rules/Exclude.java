package de.delinero.copt.rules;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.core.BasicRule;

public class Exclude extends BasicRule {

    public Exclude() {
        super("Exclude");
    }

    @Override
    public boolean evaluate(Facts facts) {
        return super.evaluate(facts);
    }

    @Override
    public void execute(Facts facts) throws Exception {
        super.execute(facts);
    }
}