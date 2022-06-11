package com.example.demo.model.history;

import javax.validation.constraints.NotNull;

public class HistoryRequest {

	@NotNull
	private Integer limit;
	@NotNull
	private Integer offset;

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

}
