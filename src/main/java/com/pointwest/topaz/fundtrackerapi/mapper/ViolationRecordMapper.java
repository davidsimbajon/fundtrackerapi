package com.pointwest.topaz.fundtrackerapi.mapper;

import com.pointwest.topaz.fundtrackerapi.dto.ViolationRecordResponse;
import com.pointwest.topaz.fundtrackerapi.model.ViolationRecord;
import com.pointwest.topaz.fundtrackerapi.model.enums.Pod;
import com.pointwest.topaz.fundtrackerapi.model.enums.ViolationCode;
import com.pointwest.topaz.fundtrackerapi.utils.Constants;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface ViolationRecordMapper {

    DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Mapping(target = "memberName", source = "violator.name")
    @Mapping(target = "pod", source = "violator.pod", qualifiedByName = "formatPod")
    @Mapping(target = "violationName", source = "violationType.violationCode",
            qualifiedByName = "formatViolationName")
    @Mapping(target = "amount", source = "violationType.defaultAmount",
            qualifiedByName = "formatAmount")
    @Mapping(target = "dateLogged", source = "dateLogged",
            qualifiedByName = "formatDate")
    @Mapping(target = "timeLogged", source = "dateLogged",
            qualifiedByName = "formatTime")
    ViolationRecordResponse toResponse(ViolationRecord violationRecord);

    @Named("formatPod")
    default String formatPod(Pod pod) {
        return switch (pod) {
            case EASY_BIZ -> Constants.EASY_BIZ;
            case EASY_WAY -> Constants.EASY_WAY;
            case EMBEDDED -> Constants.EMBEDDED;
        };
    }

    @Named("formatViolationName")
    default String formatViolationName(ViolationCode code) {
        return switch (code) {
            case LATE_POLL -> Constants.LATE_POLL;
            case LATE_DSU -> Constants.LATE_DSU;
            case NO_SHOW_DSU -> Constants.NO_SHOW_DSU;
            case NO_EMAIL_SWAP -> Constants.NO_EMAIL_SWAP;
            case LATE_WHIZ -> Constants.LATE_WHIZ;
            case DTR_CORRECTION -> Constants.DTR_CORRECTION;
            case LATE_TI_TO -> Constants.LATE_TI_TO;
        };
    }

    @Named("formatAmount")
    default BigDecimal formatAmount(double amount) {
        return BigDecimal.valueOf(amount)
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Named("formatDate")
    default String formatDate(LocalDateTime dateTime) {
        return dateTime.toLocalDate().format(DATE_FORMAT);
    }

    @Named("formatTime")
    default String formatTime(LocalDateTime dateTime) {
        return dateTime.toLocalTime().format(TIME_FORMAT);
    }
}
