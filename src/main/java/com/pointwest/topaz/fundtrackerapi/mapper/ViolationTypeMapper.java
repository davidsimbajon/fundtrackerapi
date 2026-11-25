package com.pointwest.topaz.fundtrackerapi.mapper;

import com.pointwest.topaz.fundtrackerapi.dto.ViolationTypeResponse;
import com.pointwest.topaz.fundtrackerapi.model.ViolationType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ViolationTypeMapper {

    @Mapping(target = "id", source = "violationType.id")
    @Mapping(target = "code", source = "violationType.violationCode")
    @Mapping(target = "description", source = "violationType.description")
    ViolationTypeResponse toResponse(ViolationType violationType);

}
