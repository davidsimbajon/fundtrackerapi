package com.pointwest.topaz.fundtrackerapi.controller;

import com.pointwest.topaz.fundtrackerapi.dto.CreateMemberRequest;
import com.pointwest.topaz.fundtrackerapi.dto.PaginatedResponse;
import com.pointwest.topaz.fundtrackerapi.dto.TeamMemberResponse;
import com.pointwest.topaz.fundtrackerapi.dto.UpdateMemberRequest;
import com.pointwest.topaz.fundtrackerapi.service.TeamMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@Validated
@Tag(name = "Team Members", description = "Create and manage team members and their outstanding balances")
public class TeamMemberController {

    private final TeamMemberService teamMemberService;

    @PostMapping
    @Operation(
            summary = "Add a team member",
            description = "Creates a new team member with ACTIVE status and an initial outstanding balance of zero. "
                    + "The pod and name are required."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Team member created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TeamMemberResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed because the name or pod is missing or invalid",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    public ResponseEntity<TeamMemberResponse> addMember(
            @Valid @RequestBody CreateMemberRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(teamMemberService.createTeamMember(request));
    }

    @GetMapping
    @Operation(
            summary = "List team members",
            description = "Returns a paginated list of members whose status is not DELETED. "
                    + "The response includes each member's current outstanding balance."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Team members returned successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PaginatedResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    public ResponseEntity<PaginatedResponse<TeamMemberResponse>> getAllTeamMembers(
            @Parameter(description = "Zero-based page number", example = "0")
            @PositiveOrZero @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of elements per page (default: 10)", example = "10")
            @Positive @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(teamMemberService.getAllTeamMember(page, size));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get a team member",
            description = "Returns an active team member identified by its numeric ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Team member found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TeamMemberResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No active team member exists for the supplied ID",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    public ResponseEntity<TeamMemberResponse> getTeamMemberById(
            @Parameter(description = "Unique team member ID", required = true, example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(teamMemberService.getTeamMemberById(id));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update a team member",
            description = "Updates the name and pod of an active team member. "
                    + "The outstanding balance and status are not changed by this operation."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Team member updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TeamMemberResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed because the name or pod is missing or invalid",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No active team member exists for the supplied ID",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    public ResponseEntity<TeamMemberResponse> updateTeamMember(
            @Parameter(description = "Unique team member ID", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody UpdateMemberRequest request) {
        return ResponseEntity.ok(teamMemberService.updateTeamMember(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a team member",
            description = "Soft-deletes a team member by changing its status to DELETED. "
                    + "The member remains in the database and is excluded from normal member listings."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Team member deleted successfully"),
            @ApiResponse(
                    responseCode = "404",
                    description = "No active team member exists for the supplied ID",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    public ResponseEntity<Void> deleteTeamMember(
            @Parameter(description = "Unique team member ID", required = true, example = "1")
            @PathVariable Long id) {
        teamMemberService.deleteTeamMember(id);
        return ResponseEntity.noContent().build();
    }

}
