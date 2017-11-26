package de.delinero.copt.exceptions;

public class PayloadFileException extends RuntimeException {

    public PayloadFileException(Exception exception) {
        System.err.println(String.format("Failed opening payload file, aborting.%n%s", exception.getMessage()));
    }

}
