package com.gft.desafioapi.dto;

import io.swagger.annotations.ApiModelProperty;

public class AbstractDtoId {

	@ApiModelProperty(example = "1", position = -1)
	private Long id;

	public AbstractDtoId() {}

	public AbstractDtoId(
			Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
