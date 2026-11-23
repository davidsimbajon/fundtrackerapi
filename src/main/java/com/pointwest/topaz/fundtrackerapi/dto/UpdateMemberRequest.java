package com.pointwest.topaz.fundtrackerapi.dto;

import com.pointwest.topaz.fundtrackerapi.model.enums.Pod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateMemberRequest(
        @NotBlank
        String name,

        @NotNull
        Pod pod

) {
}
