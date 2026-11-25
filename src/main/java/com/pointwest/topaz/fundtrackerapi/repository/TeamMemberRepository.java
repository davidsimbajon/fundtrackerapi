package com.pointwest.topaz.fundtrackerapi.repository;

import com.pointwest.topaz.fundtrackerapi.model.TeamMember;
import com.pointwest.topaz.fundtrackerapi.model.enums.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    @Modifying
    @Query("UPDATE TeamMember t SET t.outstandingBalance = t.outstandingBalance + :amount WHERE t.id = :memberId")
    void addBalance(@Param("memberId") Long memberId, @Param("amount") double amount);

    @Modifying
    @Query("UPDATE TeamMember t SET t.outstandingBalance = t.outstandingBalance - :amount WHERE t.id = :memberId")
    void subtractBalance(@Param("memberId") Long memberId, @Param("amount") double amount);

    List<TeamMember> findByStatusNot(MemberStatus status);
    Page<TeamMember> findByStatusNot(MemberStatus status, Pageable pageable);
    Optional<TeamMember> findByIdAndStatusNot(Long id, MemberStatus status);
}
