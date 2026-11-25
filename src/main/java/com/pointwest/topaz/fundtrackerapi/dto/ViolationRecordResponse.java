package com.pointwest.topaz.fundtrackerapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record ViolationRecordResponse(
        Long id,
        String memberName,
        @Schema(allowableValues = {"EasyBiz", "EasyWay", "Embedded"})
        String pod,
        @Schema(allowableValues = {
                "Late Poll",
                "Late DSU",
                "No Show DSU",
                "No Email Swap",
                "Late Whiz",
                "DTR Correction"
        })
        String violationName,
        BigDecimal amount,
        String dateLogged,
        String timeLogged
) {
}
