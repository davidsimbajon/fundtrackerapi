package com.pointwest.topaz.fundtrackerapi.dto;

import java.math.BigDecimal;

public record ViolationRecordResponse(
        Long id,
        String memberName,
        String pod,
        String violationName,
        BigDecimal amount,
        String dateLogged,
        String timeLogged
) {
}
