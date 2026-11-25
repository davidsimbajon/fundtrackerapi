package com.pointwest.topaz.fundtrackerapi.utils;

public class Constants {

    private Constants() {
        // To prevent instantiation
    }

    public static final String EASY_BIZ = "EasyBiz";
    public static final String EASY_WAY = "EasyWay";
    public static final String EMBEDDED = "Embedded";

    public static final String LATE_POLL = "Late Poll";
    public static final String LATE_DSU = "Late DSU";
    public static final String NO_SHOW_DSU = "No Show DSU";
    public static final String NO_EMAIL_SWAP = "No Email Swap";
    public static final String LATE_WHIZ = "Late Whiz";
    public static final String DTR_CORRECTION = "DTR Correction";

    public static final String BUSINESS_RULE_VIOLATION = "Business Rule Violation";
    public static final String RESOURCE_NOT_FOUND = "Resource Not Found";
    public static final String VALIDATION_FAILED = "Validation Failed";

    public static final String NEGATIVE_PAYMENT_AMOUNT_DETAIL =
            "Amount paid cannot be negative";
    public static final String PAYMENT_EXCEEDS_BALANCE_DETAIL =
            "Amount paid cannot be greater than the outstanding balance";
    public static final String TEAM_MEMBER_NOT_FOUND_DETAIL =
            "Team member not found";
    public static final String VIOLATION_TYPE_NOT_FOUND_DETAIL =
            "Violation type not found";
    public static final String INVALID_REQUEST_DETAIL =
            "One or more request fields are invalid";

}
