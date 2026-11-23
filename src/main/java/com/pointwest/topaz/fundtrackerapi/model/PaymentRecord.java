package com.pointwest.topaz.fundtrackerapi.model;

import com.pointwest.topaz.fundtrackerapi.model.enums.MemberStatus;
import com.pointwest.topaz.fundtrackerapi.model.enums.ModeOfPayment;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payment_record")
public class PaymentRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_member", nullable = false)
    public TeamMember teamMember;

    @Column(nullable = false)
    public double amountPaid;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public ModeOfPayment modeOfPayment;

    @Column(nullable = false)
    public LocalDateTime paymentDate;
}
