package de.delinero.copt.models;

public class Message {

    private Message() {
        // Private constructor for static message storage class
    }

    public static String getMessageByResults(Boolean validationResult, Boolean applicationResult) {
        String message = "Result: ";

        if (validationResult && applicationResult) {
            message += "The coupon is valid and could be applied to the cart.";
        } else if (validationResult) {
            message += "The coupon is valid, but unfortunately can not be applied to the cart.";
        } else if (applicationResult) {
            message += "The coupon is unfortunately invalid, but could be applied to the cart.";
        } else {
            message += "The coupon is invalid and can not be applied to the cart.";
        }

        message += String.format(" (Validation: %s, Application: %s)", validationResult, applicationResult);

        return message;
    }

}
