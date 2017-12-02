package de.delinero.copt.models;

import de.delinero.copt.exceptions.InvalidArgumentsException;

import java.util.HashMap;
import java.util.Map;

public class Arguments {

    private static final String CART_FILENAME_KEY = "cart";
    private static final String COUPON_FILENAME_KEY = "coupon";
    private static final String SILENCE_KEY = "silence";

    private static final Boolean DEFAULT_SILENCE_VALUE = true;

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
        return (processedArguments.containsKey(SILENCE_KEY)) ?
            (Boolean) processedArguments.get(SILENCE_KEY) : DEFAULT_SILENCE_VALUE;
    }

    private Map<String, Object> processArguments(String[] args) {
        Map<String, Object> arguments = new HashMap<>();

        for (int n = 0; n < args.length; n++) {

            switch (args[n]) {
                case "-v":
                case "/V":
                case "--verbose":
                    arguments.put(SILENCE_KEY, false);
                    break;

                case "-c":
                case "/C":
                case "--cart":
                    arguments.put(CART_FILENAME_KEY, args[++n]);
                    break;

                case "-p":
                case "/P":
                case "--coupon":
                    arguments.put(COUPON_FILENAME_KEY, args[++n]);
                    break;

                default:
                    throw new InvalidArgumentsException();
            }

        }

        if (! validateArguments(arguments)) {
            throw new InvalidArgumentsException();
        }

        return arguments;
    }

    private Boolean validateArguments(Map<String, Object> arguments) {
        return (arguments.containsKey(CART_FILENAME_KEY) && arguments.containsKey(COUPON_FILENAME_KEY));
    }

}
