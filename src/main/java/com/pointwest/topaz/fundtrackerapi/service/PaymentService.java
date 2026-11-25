package com.pointwest.topaz.fundtrackerapi.service;

import com.pointwest.topaz.fundtrackerapi.dto.CreatePaymentRequest;
import com.pointwest.topaz.fundtrackerapi.dto.PaginatedResponse;
import com.pointwest.topaz.fundtrackerapi.dto.PaymentRecordResponse;
import com.pointwest.topaz.fundtrackerapi.exception.BusinessRuleViolationException;
import com.pointwest.topaz.fundtrackerapi.exception.ResourceNotFoundException;
import com.pointwest.topaz.fundtrackerapi.mapper.PaymentRecordMapper;
import com.pointwest.topaz.fundtrackerapi.model.PaymentRecord;
import com.pointwest.topaz.fundtrackerapi.model.TeamMember;
import com.pointwest.topaz.fundtrackerapi.model.enums.MemberStatus;
import com.pointwest.topaz.fundtrackerapi.repository.PaymentRecordRepository;
import com.pointwest.topaz.fundtrackerapi.repository.TeamMemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRecordRepository paymentRecordRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final PaymentRecordMapper paymentRecordMapper;

    @Transactional
    public PaymentRecordResponse logPayment(CreatePaymentRequest request) {
        validateNegativeAmount(request.amountPaid());

        TeamMember member = teamMemberRepository.findByIdAndStatusNot(request.memberId(), MemberStatus.DELETED)
                .orElseThrow(() -> new ResourceNotFoundException(
                        com.pointwest.topaz.fundtrackerapi.utils.Constants.TEAM_MEMBER_NOT_FOUND_DETAIL,
                        "Team member ID: " + request.memberId()));

        validatePaymentDoesNotExceedBalance(request.amountPaid(), member.getOutstandingBalance());

        PaymentRecord record = PaymentRecord.builder()
                .teamMember(member)
                .amountPaid(request.amountPaid())
                .modeOfPayment(request.modeOfPayment())
                .paymentDate(getCurrentPaymentDate())
                .build();

        PaymentRecord savedRecord = paymentRecordRepository.save(record);
        teamMemberRepository.subtractBalance(member.getId(), request.amountPaid());

        return paymentRecordMapper.toResponse(savedRecord);
    }

    public PaginatedResponse<PaymentRecordResponse> getAllPayments(int page, int size) {
        Page<PaymentRecord> paymentPage = paymentRecordRepository.findAll(PageRequest.of(page, size));
        List<PaymentRecordResponse> content = paymentPage.getContent()
                .stream()
                .map(paymentRecordMapper::toResponse)
                .toList();

        return new PaginatedResponse<>(
                content,
                paymentPage.getNumber(),
                paymentPage.getTotalPages(),
                paymentPage.getSize()
        );
    }

    private void validateNegativeAmount(double amountPaid) {
        if (amountPaid < 0) {
            throw new BusinessRuleViolationException(
                    com.pointwest.topaz.fundtrackerapi.utils.Constants.NEGATIVE_PAYMENT_AMOUNT_DETAIL);
        }
    }

    private LocalDateTime getCurrentPaymentDate() {
        return LocalDateTime.now();
    }

    private void validatePaymentDoesNotExceedBalance(double amountPaid, double outstandingBalance) {
        if (amountPaid > outstandingBalance) {
            throw new BusinessRuleViolationException(
                    com.pointwest.topaz.fundtrackerapi.utils.Constants.PAYMENT_EXCEEDS_BALANCE_DETAIL);
        }
    }
}
