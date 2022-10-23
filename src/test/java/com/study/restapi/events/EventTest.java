package com.study.restapi.events;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EventTest {

    @Test
    @DisplayName("Lombok 테스트 1 : 빌더 객체 테스트")
    public void builder() {
        Event event = Event.builder()
                .name("Sprint Rest API")
                .description("REST API development with Spring")
                .build();
        assertThat(event).isNotNull();
    }

    @Test
    @DisplayName("Lombok 테스트 2 : 생성자 생성 테스트")
    public void javaBean() {

        // Given
        String name = "Event";
        String descriptioin = "Spring";

        // When
        Event event = new Event();
        event.setName(name);
        event.setDescription(descriptioin);

        // Then
        assertThat(event.getName()).isEqualTo(name);
        assertThat(event.getDescription()).isEqualTo(descriptioin);
    }
}