package com.pointwest.topaz.fundtrackerapi.service;

import com.pointwest.topaz.fundtrackerapi.dto.CreateMemberRequest;
import com.pointwest.topaz.fundtrackerapi.dto.TeamMemberResponse;
import com.pointwest.topaz.fundtrackerapi.dto.UpdateMemberRequest;
import com.pointwest.topaz.fundtrackerapi.mapper.TeamMemberMapper;
import com.pointwest.topaz.fundtrackerapi.model.TeamMember;
import com.pointwest.topaz.fundtrackerapi.model.enums.MemberStatus;
import com.pointwest.topaz.fundtrackerapi.repository.TeamMemberRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamMemberService {

    private final TeamMemberRepository teamMemberRepository;
    private final TeamMemberMapper teamMemberMapper;

    @Transactional
    public TeamMemberResponse createTeamMember(CreateMemberRequest request) {
        TeamMember member = teamMemberMapper.toEntity(request);
        member.setStatus(MemberStatus.ACTIVE);
        return teamMemberMapper.toResponse(teamMemberRepository.save(member));
    }

    public List<TeamMemberResponse> getAllTeamMember() {
        return teamMemberRepository.findByStatusNot(MemberStatus.DELETED)
                .stream()
                .map(teamMemberMapper::toResponse)
                .toList();
    }

    public TeamMemberResponse getTeamMemberById(Long id) {
        return teamMemberMapper.toResponse(findActiveMember(id));
    }

    @Transactional
    public TeamMemberResponse updateTeamMember(Long id, UpdateMemberRequest request) {
        TeamMember member = findActiveMember(id);
        teamMemberMapper.updateEntity(request, member);
        return teamMemberMapper.toResponse(teamMemberRepository.save(member));
    }

    @Transactional
    public void deleteTeamMember(Long id) {
        TeamMember member = findActiveMember(id);
        member.setStatus(MemberStatus.DELETED);
        teamMemberRepository.save(member);
    }

    private TeamMember findActiveMember(Long id){
        return teamMemberRepository.findByIdAndStatusNot(id, MemberStatus.DELETED)
                .orElseThrow(() ->
                        new EntityNotFoundException("Team member not found with id: " + id));
    }

}
