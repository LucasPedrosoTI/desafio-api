package com.gft.desafioapi.utils;

import java.io.IOException;

import org.springframework.dao.EmptyResultDataAccessException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.gft.desafioapi.model.Fornecedor;

public class CustomProdutoDeserializer extends StdDeserializer<Fornecedor> {

	private static final long serialVersionUID = -839983745171929649L;

	public CustomProdutoDeserializer() {
		this(null);
	}

	public CustomProdutoDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public Fornecedor deserialize(JsonParser jsonparser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		Long id = null;
		JsonNode node = jsonparser.getCodec().readTree(jsonparser);

		if (node.has("id")) {
			id = node.get("id").asLong();
		} else {
			throw new EmptyResultDataAccessException("ID do fornecedor não informado", 1);
		}

		return new Fornecedor(id, null, null, null);
	}

}
