package com.pointwest.topaz.fundtrackerapi.repository;

import com.pointwest.topaz.fundtrackerapi.model.ViolationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ViolationTypeRepository extends JpaRepository<ViolationType, Long> {
    Optional<ViolationType> findByCode(String code);
}
