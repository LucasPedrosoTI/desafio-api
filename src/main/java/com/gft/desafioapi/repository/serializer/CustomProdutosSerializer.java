package com.gft.desafioapi.repository.serializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.gft.desafioapi.model.Produto;

public class CustomProdutosSerializer extends StdSerializer<List<Produto>> {

	private static final long serialVersionUID = 1057276000702591921L;

	public CustomProdutosSerializer() {
		this(null);
	}

	public CustomProdutosSerializer(Class<List<Produto>> t) {
		super(t);
	}

	@Override
	public void serialize(List<Produto> produtos, JsonGenerator generator, SerializerProvider provider)
			throws IOException {

		List<Map<String, Object>> response = new ArrayList<>();

		for (Produto produto : produtos) {
			response.add(Map.of("id", produto.getId()));
		}

		generator.writeObject(response);

	}

}
