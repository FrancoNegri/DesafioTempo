package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.event.Event;
import com.example.demo.model.history.HistoryRequest;
import com.example.demo.repos.EventRepository;

@RestController
public class HistoryController {

	@Autowired
	private EventRepository history;

	@GetMapping(path = "/history")
	public List<Event> history(@RequestBody HistoryRequest request) {
		return history.getPage(request.getLimit(), request.getOffset());
	}

}
