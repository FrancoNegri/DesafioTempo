package com.example.demo.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.model.user.CreateUserResponse;
import com.example.demo.model.user.User;
import com.example.demo.repos.UserRepository;


@RestController
public class ApplicationUser {

	@Autowired
	private UserRepository userRepository;

	@PostMapping(path = "/users")
	public CreateUserResponse createUser(@RequestBody User user) {
		logger.info("Attepting to create user: " + user);
		if (userRepository.exists(Example.of(user, ExampleMatcher.matching().withIgnorePaths("password")))) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists");
		}
		user.hashPassword();
		userRepository.save(user);
		return new CreateUserResponse(0);
	}

	private final Logger logger = LoggerFactory.getLogger(ApplicationUser.class);

}
