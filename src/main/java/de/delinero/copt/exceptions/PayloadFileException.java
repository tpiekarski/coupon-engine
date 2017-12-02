package de.delinero.copt.exceptions;

public class PayloadFileException extends Exception {

    private final String filename;

    public PayloadFileException(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }
}
