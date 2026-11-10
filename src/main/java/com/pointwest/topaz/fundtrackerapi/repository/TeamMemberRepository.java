package com.pointwest.topaz.fundtrackerapi.repository;

import com.pointwest.topaz.fundtrackerapi.model.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    @Modifying
    @Query("UPDATE TeamMember t SET t.outstandingBalance = t.outstandingBalance + :amount WHERE t.id = :memberId")
    void addBalance(@Param("memberId") Long memberId, @Param("amount") double amount);

    @Modifying
    @Query("UPDATE TeamMember t SET t.outstandingBalance = t.outstandingBalance - :amount WHERE t.id = :memberId")
    void subtractBalance(@Param("memberId") Long memberId, @Param("amount") double amount);
}
