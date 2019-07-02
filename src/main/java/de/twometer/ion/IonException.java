package de.twometer.ion;

public class IonException extends RuntimeException {

    public IonException() {
    }

    public IonException(String message) {
        super(message);
    }

    public IonException(String message, Throwable cause) {
        super(message, cause);
    }

    public IonException(Throwable cause) {
        super("The ion engine has encountered a fatal error and has to stop", cause);
    }

    public IonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
