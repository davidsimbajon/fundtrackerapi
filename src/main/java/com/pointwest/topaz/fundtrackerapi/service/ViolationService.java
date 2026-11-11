package com.pointwest.topaz.fundtrackerapi.service;

import com.pointwest.topaz.fundtrackerapi.dto.CreateViolationRequest;
import com.pointwest.topaz.fundtrackerapi.dto.ViolationRecordResponse;
import com.pointwest.topaz.fundtrackerapi.dto.ViolationTypeResponse;
import com.pointwest.topaz.fundtrackerapi.mapper.ViolationRecordMapper;
import com.pointwest.topaz.fundtrackerapi.mapper.ViolationTypeMapper;
import com.pointwest.topaz.fundtrackerapi.model.TeamMember;
import com.pointwest.topaz.fundtrackerapi.model.ViolationRecord;
import com.pointwest.topaz.fundtrackerapi.model.ViolationType;
import com.pointwest.topaz.fundtrackerapi.repository.TeamMemberRepository;
import com.pointwest.topaz.fundtrackerapi.repository.ViolationRecordRepository;
import com.pointwest.topaz.fundtrackerapi.repository.ViolationTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ViolationService {

    private final ViolationRecordRepository violationRecordRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final ViolationTypeRepository violationTypeRepository;
    private final ViolationRecordMapper violationRecordMapper;
    private final ViolationTypeMapper violationTypeMapper;

    @Transactional
    public ViolationRecordResponse logViolation(CreateViolationRequest request) {
        TeamMember violator = teamMemberRepository.findById(request.memberId())
                .orElseThrow(() -> new EntityNotFoundException("Team member not found with ID: " + request.memberId()));

        ViolationType violationType = violationTypeRepository.findById(request.violationTypeId())
                .orElseThrow(() -> new EntityNotFoundException("Violation type not found with ID: " + request.violationTypeId()));

        ViolationRecord record = buildViolationRecord(violator, violationType);

        ViolationRecord savedRecord = violationRecordRepository.save(record);

        teamMemberRepository.addBalance(violator.getId(), violationType.getDefaultAmount());

        return violationRecordMapper.toResponse(savedRecord);
    }

    public List<ViolationRecordResponse> getAllViolations() {
        return violationRecordRepository.findAll()
                .stream()
                .map(violationRecordMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<ViolationTypeResponse> getViolationTypes() {
        return violationTypeRepository.findAll()
                .stream()
                .map(violationTypeMapper::toResponse)
                .collect(Collectors.toList());
    }

    private ViolationRecord buildViolationRecord(TeamMember violator, ViolationType violationType) {
        return ViolationRecord.builder()
                .violator(violator)
                .violationType(violationType)
                .dateLogged(LocalDateTime.now())
                .build();
    }



}
