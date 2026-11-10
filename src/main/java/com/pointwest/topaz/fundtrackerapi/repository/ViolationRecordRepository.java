package com.pointwest.topaz.fundtrackerapi.repository;

import com.pointwest.topaz.fundtrackerapi.model.ViolationRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ViolationRecordRepository extends JpaRepository<ViolationRecord, Long> {
    List<ViolationRecord> findByDateLoggedBetween(LocalDateTime start, LocalDateTime end);
}
