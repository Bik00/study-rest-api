package com.study.restapi.events.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.restapi.events.Event;
import com.study.restapi.events.repository.EventRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = {EventController.class})
class EventControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    EventRepository eventRepository;

    @Test
    @DisplayName("성공적으로 이벤트 생성 시, 201 응답 코드를 반환합니다.")
    public void createEvent() throws Exception {

        // Given
        Event event = Event.builder()
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

        // When
        event.setId(10);
        Mockito.when(eventRepository.save(event)).thenReturn(event);

        // Then
        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaTypes.HAL_JSON) // HAL 적용
                        .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE));
    }
}