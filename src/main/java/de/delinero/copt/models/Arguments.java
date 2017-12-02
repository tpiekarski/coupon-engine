package de.delinero.copt.models;

import de.delinero.copt.exceptions.InvalidArgumentsException;

import java.util.*;

public class Arguments {

    private static final String CART_FILENAME_KEY = "cart";
    private static final String COUPON_FILENAME_KEY = "coupon";
    private static final String SILENCE_KEY = "silence";

    private final Map<String, Object> processedArguments;

    public Arguments(String[] args) {
        this.processedArguments = processArguments(args);
    }

    public String getCartFilename() {
        return (String) processedArguments.get(CART_FILENAME_KEY);
    }

    public String getCouponFilename() {
        return (String) processedArguments.get(COUPON_FILENAME_KEY);
    }

    public Boolean getSilence() {
        return (Boolean) processedArguments.get(SILENCE_KEY);
    }

    private Map<String, Object> processArguments(String[] args) {
        Map<String, Object> arguments = new HashMap<>();
        
        if (args.length == 2) {
            arguments.put(CART_FILENAME_KEY, args[0]);
            arguments.put(COUPON_FILENAME_KEY, args[1]);
            arguments.put(SILENCE_KEY, true);
        } else if (args.length == 3) {
            arguments.put(CART_FILENAME_KEY, args[0]);
            arguments.put(COUPON_FILENAME_KEY, args[1]);
            arguments.put(SILENCE_KEY, Boolean.valueOf(args[2]));
        } else {
            throw new InvalidArgumentsException();
        }
        
        return arguments;
    }

}
