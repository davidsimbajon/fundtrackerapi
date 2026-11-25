package com.pointwest.topaz.fundtrackerapi.dto;

import com.pointwest.topaz.fundtrackerapi.model.enums.ModeOfPayment;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record PaymentRecordResponse(
        Long id,
        String memberName,
        @Schema(allowableValues = {"EasyBiz", "EasyWay", "Embedded"})
        String pod,
        BigDecimal amountPaid,
        ModeOfPayment modeOfPayment,
        String paymentDate,
        String paymentTime
) {
}
