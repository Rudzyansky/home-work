package com.sbrf.reboot.exceptions;

public class FileAccountRepositoryException extends RuntimeException {
    public FileAccountRepositoryException() {
    }

    public FileAccountRepositoryException(String message) {
        super(message);
    }

    public FileAccountRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileAccountRepositoryException(Throwable cause) {
        super(cause);
    }

    public FileAccountRepositoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
