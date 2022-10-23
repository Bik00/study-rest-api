package com.study.restapi.events.repository;

import com.study.restapi.events.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {

}
