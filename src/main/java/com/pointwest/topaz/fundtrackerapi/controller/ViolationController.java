package com.pointwest.topaz.fundtrackerapi.controller;

import com.pointwest.topaz.fundtrackerapi.dto.CreateViolationRequest;
import com.pointwest.topaz.fundtrackerapi.dto.ViolationRecordResponse;
import com.pointwest.topaz.fundtrackerapi.dto.ViolationTypeResponse;
import com.pointwest.topaz.fundtrackerapi.service.ViolationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/violations")
@RequiredArgsConstructor
public class ViolationController {

    private final ViolationService violationService;

    @PostMapping
    public ResponseEntity<ViolationRecordResponse> logViolation(@Valid @RequestBody CreateViolationRequest request) {
        ViolationRecordResponse response = violationService.logViolation(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ViolationRecordResponse>> getAllViolations() {
        return ResponseEntity.ok(violationService.getAllViolations());
    }

    @GetMapping("/violation-type")
    public ResponseEntity<List<ViolationTypeResponse>> getAllViolationTypes() {
        return ResponseEntity.ok(violationService.getViolationTypes());
    }

}
