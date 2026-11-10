package com.pointwest.topaz.fundtrackerapi.model;

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

    @ColumnDefault("0")
    public double outstandingBalance;

}
