package com.pointwest.topaz.fundtrackerapi.dto;

import com.pointwest.topaz.fundtrackerapi.model.enums.MemberStatus;

public record TeamMemberResponse(
        Long id,
        String name,
        String pod,
        double outstandingBalance,
        MemberStatus status
) {
}
