package com.pointwest.topaz.fundtrackerapi.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.pointwest.topaz.fundtrackerapi.model.enums.ModeOfPayment;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreatePaymentRequest(
        @NotNull
        @JsonAlias("teamMemberId")
        Long memberId,

        @NotNull
        @Schema(description = "Payment method", allowableValues = {"GCASH", "CASH"})
        ModeOfPayment modeOfPayment,

        @NotNull
        @Positive
        Double amountPaid
) {
    public Long teamMemberId() {
        return memberId;
    }
}
