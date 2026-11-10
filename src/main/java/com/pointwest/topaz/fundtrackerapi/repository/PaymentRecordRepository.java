package com.pointwest.topaz.fundtrackerapi.repository;

import com.pointwest.topaz.fundtrackerapi.model.PaymentRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentRecordRepository extends JpaRepository<PaymentRecord, Long> {
    List<PaymentRecord> findByPaymentDateBetween(LocalDateTime start, LocalDateTime end);
}
