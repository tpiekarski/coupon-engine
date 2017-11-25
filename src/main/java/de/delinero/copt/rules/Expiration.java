package de.delinero.copt.rules;

import de.delinero.copt.models.Coupon;
import de.delinero.copt.models.CouponRule;
import de.delinero.copt.models.CouponRuleSet;
import org.jeasy.rules.api.Facts;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;

public class Expiration extends AbstractCouponRule {

    public Expiration() {
        super("Expiration");
    }

    @Override
    public boolean evaluate(Facts facts) {
        CouponRuleSet ruleSet = (CouponRuleSet) facts.get("ruleSet");

        Optional<CouponRule> rule = ruleSet.getRuleByName(this.name);

        if (! rule.isPresent()) {
            return false;
        }

        Date today = new Date();
        Date parsedDate = parseDate(rule.get().getOption());

        return today.before(parsedDate);
    }

    private Date parseDate(String date) {

        if (! System.getProperty("user.timezone").equals("UTC")) {
            TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        }

        return Date.from(
            LocalDate.parse(
                date, DateTimeFormatter.ofPattern("yyyy-MM-dd")
            ).atStartOfDay().toInstant(ZoneOffset.UTC)
        );
    }

}
