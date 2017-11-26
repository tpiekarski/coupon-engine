package de.delinero.copt.rules;

import de.delinero.copt.engines.CouponEngine;
import de.delinero.copt.models.EvaluatedResult;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.core.BasicRule;

import java.util.List;

public abstract class AbstractCouponRule extends BasicRule {

    public AbstractCouponRule(String name) {
        super(name);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void execute(Facts facts) throws Exception {
        List<EvaluatedResult> results = (List<EvaluatedResult>) facts.get(CouponEngine.RESULTS);

        results.stream()
            .filter(result -> result.getRuleName().equals(this.name))
            .findFirst()
            .ifPresent(evaluatedResult -> evaluatedResult.setResult(true));

        facts.remove(CouponEngine.RESULTS);
        facts.put(CouponEngine.RESULTS, results);
    }

}
