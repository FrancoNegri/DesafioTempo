package com.example.demo.model.sum;

import javax.validation.constraints.NotNull;

public class SumRequest {

	@NotNull
	private Integer element1;
	@NotNull
	private Integer element2;

	public Integer getElement1() {
		return element1;
	}

	public void setElement1(Integer element1) {
		this.element1 = element1;
	}

	public Integer getElement2() {
		return element2;
	}

	public void setElement2(Integer element2) {
		this.element2 = element2;
	}

}
