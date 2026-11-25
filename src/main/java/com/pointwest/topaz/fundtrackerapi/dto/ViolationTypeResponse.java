package com.pointwest.topaz.fundtrackerapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ViolationTypeResponse(
        Long id,
        @Schema(allowableValues = {
                "LATE_POLL",
                "LATE_DSU",
                "NO_SHOW_DSU",
                "NO_EMAIL_SWAP",
                "LATE_WHIZ",
                "DTR_CORRECTION",
                "LATE_TI_TO"
        })
        String code,
        String description
) {
}
