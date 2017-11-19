package de.delinero.copt.engines;

import de.delinero.copt.models.EvaluatedResult;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.List;

public class CouponExpressionEngine {

    private final ExpressionParser parser;

    public CouponExpressionEngine(ExpressionParser parser) {
        this.parser = parser;
    }

    public Boolean parse(String couponExpression, List<EvaluatedResult> evaluatedResults) {
        StandardEvaluationContext context = new StandardEvaluationContext();
        evaluatedResults.forEach((result) -> context.setVariable(result.getRuleName(), result.getResult()));

        return (Boolean) parser.parseExpression(couponExpression).getValue(context);
    }

}
