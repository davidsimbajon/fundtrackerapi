package com.pointwest.topaz.fundtrackerapi.controller;

import com.pointwest.topaz.fundtrackerapi.dto.CreatePaymentRequest;
import com.pointwest.topaz.fundtrackerapi.dto.PaginatedResponse;
import com.pointwest.topaz.fundtrackerapi.dto.PaymentRecordResponse;
import com.pointwest.topaz.fundtrackerapi.service.PaymentService;
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
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Validated
@Tag(name = "Payments", description = "Record payments and retrieve payment history")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    @Operation(
            summary = "Log a payment",
            description = "Records a payment against an active team member and reduces that member's "
                    + "outstanding balance by the amount paid. "
                    + "The payment must be positive and cannot exceed the member's outstanding balance. "
                    + "The payment date and time are set by the backend to the current time."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Payment recorded successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PaymentRecordResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request or business rule violation. This includes missing/invalid fields, "
                            + "a negative or non-positive amount, or a payment greater than the outstanding balance",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "The referenced team member does not exist or is deleted",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    public ResponseEntity<PaymentRecordResponse> logPayment(
            @Valid @RequestBody CreatePaymentRequest request) {
        PaymentRecordResponse response = paymentService.logPayment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(
            summary = "List all payments",
            description = "Returns a paginated payment history, including member, pod, amount, payment mode, "
                    + "and payment date/time."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Payment records returned successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PaginatedResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    public ResponseEntity<PaginatedResponse<PaymentRecordResponse>> getAllPayments(
            @Parameter(description = "Zero-based page number", example = "0")
            @PositiveOrZero @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of elements per page (default: 10)", example = "10")
            @Positive @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(paymentService.getAllPayments(page, size));
    }
}
