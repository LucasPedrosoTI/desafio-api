package com.gft.desafioapi.utils;

import java.util.Objects;

import com.gft.desafioapi.model.AbstractEntity;

public class EntityUtils {

	public static <T> T coalesce(T one, T two) {
		return one != null ? one : two;
	}

	public static <T extends AbstractEntity> void setIdNull(T entity) {
		if (Objects.nonNull(entity.getId())) {
			entity.setId(null);
		}
	}
}
