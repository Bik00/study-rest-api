package com.study.restapi.events;

import org.hibernate.annotations.DialectOverride;
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

    @Test
    @DisplayName("basePrice와 maxPrice가 0이면 무료, 아니면 유료 입니다.")
    public void testFree() {

        // Given
        Event event = Event.builder()
                .basePrice(0)
                .maxPrice(0)
                .build();

        // When
        event.update();

        // Then
        assertThat(event.isFree()).isTrue();

        // Given
        event = Event.builder()
                .basePrice(100)
                .maxPrice(0)
                .build();

        // When
        event.update();

        // Then
        assertThat(event.isFree()).isFalse();
    }

    @Test
    @DisplayName("basePrice와 maxPrice가 0이면 무료, 아니면 유료 입니다.")
    public void testOffline() {

        // Given
        Event event = Event.builder()
                .location("대구광역시 북구 복현로")
                .build();

        // When
        event.update();

        // Then
        assertThat(event.isOffline()).isTrue();

        // Given
        event = Event.builder()
                .build();

        // When
        event.update();

        // Then
        assertThat(event.isOffline()).isFalse();
    }
}