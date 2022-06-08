package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.sum.SumRequest;
import com.example.demo.model.sum.SumResult;

@RestController
public class SumController {

	@PostMapping(path = "/sum")
	public SumResult sum(@RequestBody SumRequest sumRequest) {
		int result = sumRequest.getElement1() + sumRequest.getElement2();
		logger.info("Summing..." + sumRequest.getElement1() + "+" + sumRequest.getElement2() + "=" + result);
		SumResult sumResult = new SumResult(result);
		return sumResult;
	}

	private final Logger logger = LoggerFactory.getLogger(ApplicationUser.class);

}
