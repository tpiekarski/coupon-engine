package de.delinero.copt.models;

import de.delinero.copt.exceptions.InvalidArgumentsException;

import java.util.*;

public class Arguments {

    private Map<String, Object> arguments;

    public Arguments(String[] args) {
        this.arguments = processArguments(args);
    }

    public String getCartFilename() {
        return (String) arguments.get("cart");
    }

    public String getCouponFilename() {
        return (String) arguments.get("coupon");
    }

    public Boolean getSilence() {
        return (Boolean) arguments.get("silence");
    }

    private Map<String, Object> processArguments(String[] args) {
        Map<String, Object> processedArguments = new HashMap<>();
        
        if (args.length == 2) {
            processedArguments.put("cart", args[0]);
            processedArguments.put("coupon", args[1]);
            processedArguments.put("silence", true);
        } else if (args.length == 3) {
            processedArguments.put("cart", args[0]);
            processedArguments.put("coupon", args[1]);
            processedArguments.put("silence", Boolean.valueOf(args[2]));
        } else {
            throw new InvalidArgumentsException();
        }
        
        return processedArguments;
    }

}
