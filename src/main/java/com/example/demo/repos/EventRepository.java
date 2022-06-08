package com.example.demo.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.event.Event;

public interface EventRepository extends JpaRepository<Event, Integer> {

	@Query(value = "SELECT * FROM event LIMIT ?1 OFFSET ?2", nativeQuery = true)
	List<Event> getPage(int limit, int offset);

}
