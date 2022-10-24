package com.study.restapi.events.validator;

import com.study.restapi.events.Dto.EventDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;

@Component
public class EventValidator {

    public void validate(EventDto eventDto, Errors errors) {

        if(
                eventDto.getBasePrice() > eventDto.getMaxPrice() &
                eventDto.getMaxPrice() > 0
        ) {
            // 1. 필드 에러
            errors.rejectValue("basePrice", "wrongValue", "basePrice is wrong.");
            errors.rejectValue("maxPrice", "wrongValue", "maxPrice is wrong.");

            // 2. 글로벌 에러
            // errors.reject("wrongPrices", "Values for Prices are wrong");
        }

        LocalDateTime endEventDateTime = eventDto.getEndEventDateTime();
        if(
                endEventDateTime.isBefore(eventDto.getBeginEventDateTime()) ||
                endEventDateTime.isBefore(eventDto.getCloseEnrollmentDateTime()) ||
                endEventDateTime.isBefore(eventDto.getBeginEnrollmentDateTime())
        ) {
            errors.rejectValue("endEventDateTime", "wrongValue", "endEventDateTime is wrong");
        }

        LocalDateTime beginEventDateTime = eventDto.getBeginEventDateTime();
        if(
                beginEventDateTime.isBefore(eventDto.getBeginEnrollmentDateTime()) ||
                beginEventDateTime.isBefore(eventDto.getCloseEnrollmentDateTime()) ||
                beginEventDateTime.isAfter(eventDto.getEndEventDateTime())
        ) {
            errors.rejectValue("beginEventDateTime", "wrongValue", "beginEventDateTime is wrong");
        }

        LocalDateTime closeEnrollmentDateTime = eventDto.getCloseEnrollmentDateTime();
        if(
                closeEnrollmentDateTime.isBefore(eventDto.getBeginEnrollmentDateTime())
        ) {
            errors.rejectValue("closeEnrollmentDateTime", "wrongValue", "closeEnrollmentDateTime is wrong");
        }
    }
}
