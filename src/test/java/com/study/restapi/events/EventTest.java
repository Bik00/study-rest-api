package com.study.restapi.events;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EventTest {

    @Test
    public void builder() {
        Event event = Event.builder()
                .name("Sprint Rest API")
                .description("REST API development with Spring")
                .build();
        assertThat(event).isNotNull();
    }

    @Test
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