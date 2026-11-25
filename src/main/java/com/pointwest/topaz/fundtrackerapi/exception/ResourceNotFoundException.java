package com.pointwest.topaz.fundtrackerapi.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String detail) {
        super(detail);
    }

    public ResourceNotFoundException(String detail, String resource) {
        super(detail + ": " + resource);
    }
}
