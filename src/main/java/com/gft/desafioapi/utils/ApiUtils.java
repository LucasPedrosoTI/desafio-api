package com.gft.desafioapi.utils;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.Objects;

import org.springframework.data.domain.Page;

import com.gft.desafioapi.dto.AbstractDTO;
import com.gft.desafioapi.model.AbstractEntity;

public class ApiUtils {

	public static <T> T coalesce(T one, T two) {
		return one != null ? one : two;
	}

	public static <T extends AbstractEntity> void setIdNull(T entity) {
		if (Objects.nonNull(entity.getId())) {
			entity.setId(null);
		}
	}

	public static <T extends AbstractDTO> Object createRelListAllLink(T dto, Class<?> resource) {
		return dto.add(linkTo(resource).withRel("listAll"));
	}

	public static <T extends AbstractDTO> Object createSelfLink(T dto, Class<?> resource) {
		return dto.add(linkTo(resource).slash(dto.getId())
				.withSelfRel());
	}


	public static <T extends AbstractDTO> Page<Object> createSelfLink(Page<T> dtos, Class<?> resource) {
		return dtos.map(dto -> createSelfLink(dto, resource));
	}
}
