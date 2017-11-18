package de.delinero.copt.rules;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.core.BasicRule;

import java.util.HashMap;

public abstract class AbstractCouponRule extends BasicRule {

    public AbstractCouponRule(String name) {
        super(name);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void execute(Facts facts) throws Exception {
        HashMap<String, Boolean> results = (HashMap<String, Boolean>) facts.get("results");
        results.put(name, true);
        facts.remove("results");
        facts.put("results", results);
    }

}
