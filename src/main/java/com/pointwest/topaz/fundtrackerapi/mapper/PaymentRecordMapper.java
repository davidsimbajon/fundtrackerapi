package com.pointwest.topaz.fundtrackerapi.mapper;

import com.pointwest.topaz.fundtrackerapi.dto.PaymentRecordResponse;
import com.pointwest.topaz.fundtrackerapi.model.PaymentRecord;
import com.pointwest.topaz.fundtrackerapi.model.enums.Pod;
import com.pointwest.topaz.fundtrackerapi.utils.Constants;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface PaymentRecordMapper {

    DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Mapping(target = "memberName", source = "teamMember.name")
    @Mapping(target = "pod", source = "teamMember.pod", qualifiedByName = "formatPod")
    @Mapping(target = "amountPaid", source = "amountPaid", qualifiedByName = "formatAmount")
    @Mapping(target = "paymentDate", source = "paymentDate", qualifiedByName = "formatDate")
    @Mapping(target = "paymentTime", source = "paymentDate", qualifiedByName = "formatTime")
    PaymentRecordResponse toResponse(PaymentRecord paymentRecord);

    @Named("formatPod")
    default String formatPod(Pod pod) {
        return switch (pod) {
            case EASY_BIZ -> Constants.EASY_BIZ;
            case EASY_WAY -> Constants.EASY_WAY;
            case EMBEDDED -> Constants.EMBEDDED;
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
