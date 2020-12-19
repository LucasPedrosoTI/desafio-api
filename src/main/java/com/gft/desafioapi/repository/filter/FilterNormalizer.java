package com.gft.desafioapi.repository.filter;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface FilterNormalizer {


	default Map<String, Map<String, Object>> removeNullValues(FilterNormalizer filter, FilterNormalizer standard) {

		List<Field> fields = Arrays.asList(filter.getClass().getDeclaredFields());
		Map<String, Map<String, Object>> verifiedFields = new HashMap<>();

		fields.forEach(field -> {
			try {
				field.setAccessible(true);

				Map<String, Object> verifiedAttribute = field.get(filter) != null ? Map.of("value", field.get(filter))
						: Map.of("value", field.get(standard));

				verifiedFields.putAll(Map.of(field.getName(), verifiedAttribute));

				field.setAccessible(false);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}


		});

		return verifiedFields;
	}

	default String getValueFrom(String key, Map<String, Map<String, Object>> verifiedFilter) {
		return verifiedFilter.get(key).get("value").toString();
	}

}
