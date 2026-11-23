package com.pointwest.topaz.fundtrackerapi.controller;

import com.pointwest.topaz.fundtrackerapi.dto.CreateMemberRequest;
import com.pointwest.topaz.fundtrackerapi.dto.TeamMemberResponse;
import com.pointwest.topaz.fundtrackerapi.dto.UpdateMemberRequest;
import com.pointwest.topaz.fundtrackerapi.service.TeamMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class TeamMemberController {

    private final TeamMemberService teamMemberService;

    @PostMapping
    public ResponseEntity<TeamMemberResponse> addMember(
            @Valid @RequestBody CreateMemberRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(teamMemberService.createTeamMember(request));
    }

    @GetMapping
    public ResponseEntity<List<TeamMemberResponse>> getAllTeamMembers() {
        return ResponseEntity.ok(teamMemberService.getAllTeamMember());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamMemberResponse> getTeamMemberById(@PathVariable Long id) {
        return ResponseEntity.ok(teamMemberService.getTeamMemberById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamMemberResponse> updateTeamMember(
            @PathVariable Long id,
            @Valid @RequestBody UpdateMemberRequest request) {
        return ResponseEntity.ok(teamMemberService.updateTeamMember(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeamMember(@PathVariable Long id) {
        teamMemberService.deleteTeamMember(id);
        return ResponseEntity.noContent().build();
    }

}
