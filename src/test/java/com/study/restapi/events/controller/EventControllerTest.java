package com.study.restapi.events.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.restapi.events.Dto.EventDto;
import com.study.restapi.events.Event;
import com.study.restapi.events.EventStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EventControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("성공적으로 함수 수행 시, 201 응답 코드를 반환합니다. (CREATE 과정)")
    public void createEvent() throws Exception {

        // Given
        EventDto event = EventDto.builder()
                .name("spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.now().withMinute(0).withSecond(0))
                .closeEnrollmentDateTime(LocalDateTime.now().withMinute(59).withSecond(59))
                .beginEventDateTime(LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0))
                .endEventDateTime(LocalDateTime.now().plusDays(1).withHour(23).withMinute(59).withSecond(59))
                .basePrice(100)
                .basePrice(200)
                .limitOfEnrollment(100)
                .location("서울대 입구역 스터디 카페")
                .build();

        // Then
        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaTypes.HAL_JSON) // HAL 적용
                        .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("id").value(Matchers.not(100)))
                .andExpect(jsonPath("free").value(Matchers.not(true)))
                .andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name()));
    }

    @Test
    @DisplayName("주어진 입력값 이외에는 Bad Request를 반환합니다.")
    public void createEvent_bad_request() throws Exception {

        // Given
        Event event = Event.builder()
                .id(100)
                .name("spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.now().withMinute(0).withSecond(0))
                .closeEnrollmentDateTime(LocalDateTime.now().withMinute(59).withSecond(59))
                .beginEventDateTime(LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0))
                .endEventDateTime(LocalDateTime.now().plusDays(1).withHour(23).withMinute(59).withSecond(59))
                .basePrice(100)
                .basePrice(200)
                .limitOfEnrollment(100)
                .location("서울대 입구역 스터디 카페")
                .free(true)
                .offline(false)
                .eventStatus(EventStatus.PUBLISHED)
                .build();

        // Then
        mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON) // HAL 적용
                .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @DisplayName("입력 값이 아무 것도 없다면, Bad Request를 반환합니다.")
    public void createEvent_Bad_Request_Empty_Input() throws Exception {
        EventDto eventDto = EventDto.builder().build();

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(this.objectMapper.writeValueAsString(eventDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("입력 값이 잘못 되었다면, Bad Request를 반환합니다.")
    public void createEvent_Bad_Request_Wrong_Input() throws Exception {

        // Given
        EventDto eventDto = EventDto.builder()
                .name("spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.now().plusDays(4).withMinute(0).withSecond(0))
                .closeEnrollmentDateTime(LocalDateTime.now().plusDays(3).withMinute(59).withSecond(59))
                .beginEventDateTime(LocalDateTime.now().plusDays(2).withHour(0).withMinute(0).withSecond(0))
                .endEventDateTime(LocalDateTime.now().plusDays(1).withHour(23).withMinute(59).withSecond(59))
                .basePrice(10000)
                .basePrice(200)
                .limitOfEnrollment(100)
                .location("서울대 입구역 스터디 카페")
                .build();

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(this.objectMapper.writeValueAsString(eventDto)))
                .andExpect(status().isBadRequest());
    }
}