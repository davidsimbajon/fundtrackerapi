package com.pointwest.topaz.fundtrackerapi.model;

import com.pointwest.topaz.fundtrackerapi.model.enums.ViolationCode;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "violation_type")
public class ViolationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Enumerated(EnumType.STRING)
    public ViolationCode violationCode;

    @Column(nullable = false)
    public String description;

    @Column(nullable = false)
    public double defaultAmount;
}
