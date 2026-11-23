package com.pointwest.topaz.fundtrackerapi.model;

import com.pointwest.topaz.fundtrackerapi.model.enums.MemberStatus;
import com.pointwest.topaz.fundtrackerapi.model.enums.Pod;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
@Builder
@Table(name = "team_member")
@AllArgsConstructor
@NoArgsConstructor
public class TeamMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public Pod pod;

    @ColumnDefault("0")
    public double outstandingBalance;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public MemberStatus status;

}
