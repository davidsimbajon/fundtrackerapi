package com.pointwest.topaz.fundtrackerapi.controller;

import com.pointwest.topaz.fundtrackerapi.dto.CreateViolationRequest;
import com.pointwest.topaz.fundtrackerapi.dto.PaginatedResponse;
import com.pointwest.topaz.fundtrackerapi.dto.ViolationRecordResponse;
import com.pointwest.topaz.fundtrackerapi.dto.ViolationTypeResponse;
import com.pointwest.topaz.fundtrackerapi.service.ViolationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/violations")
@RequiredArgsConstructor
@Validated
@Tag(name = "Violations", description = "Log violations and retrieve violation types and history")
public class ViolationController {

    private final ViolationService violationService;

    @PostMapping
    @Operation(
            summary = "Log a violation",
            description = "Creates a violation record for an existing team member using an existing violation type. "
                    + "The violation type's default amount is added to the member's outstanding balance."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Violation logged successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ViolationRecordResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed because memberId or violationTypeId is missing or invalid",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "The referenced team member or violation type does not exist",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    public ResponseEntity<ViolationRecordResponse> logViolation(@Valid @RequestBody CreateViolationRequest request) {
        ViolationRecordResponse response = violationService.logViolation(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(
            summary = "List all violation records",
            description = "Returns a paginated list of logged violations, including the member, pod, violation name, amount, "
                    + "and date/time logged."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Violation records returned successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PaginatedResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    public ResponseEntity<PaginatedResponse<ViolationRecordResponse>> getAllViolations(
            @Parameter(description = "Zero-based page number", example = "0")
            @PositiveOrZero @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of elements per page (default: 10)", example = "10")
            @Positive @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(violationService.getAllViolations(page, size));
    }

    @GetMapping("/violation-type")
    @Operation(
            summary = "List violation types",
            description = "Returns the configured violation types available when logging a violation."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Violation types returned successfully",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = ViolationTypeResponse.class)
                            )
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    public ResponseEntity<List<ViolationTypeResponse>> getAllViolationTypes() {
        return ResponseEntity.ok(violationService.getViolationTypes());
    }

}
