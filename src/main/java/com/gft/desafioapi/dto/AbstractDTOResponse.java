package com.gft.desafioapi.dto;

import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.annotations.ApiModelProperty;

public class AbstractDTOResponse extends RepresentationModel<AbstractDTOResponse> {

	@ApiModelProperty(example = "1", position = -1)
	private Long id;

	public AbstractDTOResponse() {}

	public AbstractDTOResponse(
			Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(id);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractDTOResponse other = (AbstractDTOResponse) obj;
		return Objects.equals(id, other.id);
	}

}
