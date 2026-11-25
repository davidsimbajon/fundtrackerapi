package com.pointwest.topaz.fundtrackerapi.dto;

import com.pointwest.topaz.fundtrackerapi.model.enums.MemberStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public record TeamMemberResponse(
        Long id,
        String name,
        @Schema(allowableValues = {"EasyBiz", "EasyWay", "Embedded"})
        String pod,
        double outstandingBalance,
        MemberStatus status
) {
}
