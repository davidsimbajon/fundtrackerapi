package com.pointwest.topaz.fundtrackerapi.dto;

import jakarta.validation.constraints.NotNull;

public record CreateViolationRequest(
        @NotNull
        Long memberId,

        @NotNull
        Long violationTypeId
)
{}
