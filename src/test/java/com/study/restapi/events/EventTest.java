package com.study.restapi.events;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
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

    @ParameterizedTest
    @CsvSource({
            "0, 0, true",
            "100, 0, false",
            "0, 100, false"
    })
    @DisplayName("basePrice와 maxPrice가 0이면 무료, 아니면 유료 입니다.")
    public void testFree(int basePrice, int maxPrice, boolean isFree) {

        // Given
        Event event = Event.builder()
                .basePrice(basePrice)
                .maxPrice(maxPrice)
                .build();

        // When
        event.update();

        // Then
        assertThat(event.isFree()).isEqualTo(isFree);
    }

    @ParameterizedTest
    @MethodSource("isOffline")
    @DisplayName("basePrice와 maxPrice가 0이면 무료, 아니면 유료 입니다.")
    public void testOffline(String location, boolean isOffline) {

        // Given
        Event event = Event.builder()
                .location(location)
                .build();

        // When
        event.update();

        // Then
        assertThat(event.isOffline()).isEqualTo(isOffline);
    }

    private static Stream<Arguments> isOffline() {
        return Stream.of(
                Arguments.of("강남역", true),
                Arguments.of(null, false),
                Arguments.of("", false)
        );
    }
}