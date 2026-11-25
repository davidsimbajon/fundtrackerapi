package com.pointwest.topaz.fundtrackerapi.dto;

import com.pointwest.topaz.fundtrackerapi.model.enums.Pod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateMemberRequest(
        @NotBlank
        String name,

        @NotNull
        @Schema(description = "Team member pod", allowableValues = {"EASY_BIZ", "EASY_WAY", "EMBEDDED"})
        Pod pod

) {
}
