package com.pointwest.topaz.fundtrackerapi.mapper;

import com.pointwest.topaz.fundtrackerapi.dto.CreateMemberRequest;
import com.pointwest.topaz.fundtrackerapi.dto.TeamMemberResponse;
import com.pointwest.topaz.fundtrackerapi.dto.UpdateMemberRequest;
import com.pointwest.topaz.fundtrackerapi.model.TeamMember;
import com.pointwest.topaz.fundtrackerapi.model.enums.Pod;
import com.pointwest.topaz.fundtrackerapi.utils.Constants;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface TeamMemberMapper {

    @Mapping(target = "pod", source = "pod", qualifiedByName = "formatPod")
    TeamMemberResponse toResponse(TeamMember member);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "outstandingBalance", ignore = true)
    TeamMember toEntity(CreateMemberRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "outstandingBalance", ignore = true)
    void updateEntity(UpdateMemberRequest request, @MappingTarget TeamMember member);


    @Named("formatPod")
    default String formatPod(Pod pod) {
        return switch (pod) {
            case EASY_BIZ -> Constants.EASY_BIZ;
            case EASY_WAY -> Constants.EASY_WAY;
            case EMBEDDED -> Constants.EMBEDDED;
        };
    }

}
