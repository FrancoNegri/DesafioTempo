package com.example.demo.model.sum;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class SumResult {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private final int result;

	public SumResult(int result) {
		this.result = result;
	}


	public int getResult() {
		return result;
	}

}
