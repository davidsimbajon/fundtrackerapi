package com.pointwest.topaz.fundtrackerapi.exception;

public class BusinessRuleViolationException extends RuntimeException {
    public BusinessRuleViolationException(String detail) {
        super(detail);
    }
}
