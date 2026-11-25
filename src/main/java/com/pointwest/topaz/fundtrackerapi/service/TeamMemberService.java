package com.pointwest.topaz.fundtrackerapi.service;

import com.pointwest.topaz.fundtrackerapi.dto.CreateMemberRequest;
import com.pointwest.topaz.fundtrackerapi.dto.PaginatedResponse;
import com.pointwest.topaz.fundtrackerapi.dto.TeamMemberResponse;
import com.pointwest.topaz.fundtrackerapi.dto.UpdateMemberRequest;
import com.pointwest.topaz.fundtrackerapi.exception.ResourceNotFoundException;
import com.pointwest.topaz.fundtrackerapi.mapper.TeamMemberMapper;
import com.pointwest.topaz.fundtrackerapi.model.TeamMember;
import com.pointwest.topaz.fundtrackerapi.model.enums.MemberStatus;
import com.pointwest.topaz.fundtrackerapi.repository.TeamMemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public PaginatedResponse<TeamMemberResponse> getAllTeamMember(int page, int size) {
        Page<TeamMember> memberPage = teamMemberRepository.findByStatusNot(
                MemberStatus.DELETED,
                PageRequest.of(page, size)
        );
        List<TeamMemberResponse> content = memberPage.getContent()
                .stream()
                .map(teamMemberMapper::toResponse)
                .toList();

        return new PaginatedResponse<>(
                content,
                memberPage.getNumber(),
                memberPage.getTotalPages(),
                memberPage.getSize()
        );
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
                .orElseThrow(() -> new ResourceNotFoundException(
                        com.pointwest.topaz.fundtrackerapi.utils.Constants.TEAM_MEMBER_NOT_FOUND_DETAIL,
                        "Team member ID: " + id));
    }

}
