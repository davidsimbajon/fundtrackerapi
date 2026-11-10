package com.pointwest.topaz.fundtrackerapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "violation_record")
public class ViolationRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "team_member", nullable = false)
    public TeamMember violator;

    @Column(nullable = false)
    public ViolationType violationType;

    @Column(nullable = false)
    public LocalDateTime dateLogged;

}
