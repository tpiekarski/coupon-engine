package de.delinero.copt.rules;

import de.delinero.copt.models.EvaluatedResult;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.core.BasicRule;

import java.util.List;

public abstract class AbstractCouponRule extends BasicRule {

    private static final String RESULTS_FACTS_KEY_NAME = "results";

    public AbstractCouponRule(String name) {
        super(name);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void execute(Facts facts) throws Exception {
        List<EvaluatedResult> results = (List<EvaluatedResult>) facts.get(RESULTS_FACTS_KEY_NAME);

        results.stream()
            .filter(result -> result.getRuleName().equals(this.name))
            .findFirst()
            .ifPresent(evaluatedResult -> evaluatedResult.setResult(true));

        facts.remove(RESULTS_FACTS_KEY_NAME);
        facts.put(RESULTS_FACTS_KEY_NAME, results);
    }

}
