package com.gft.desafioapi.utils;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.gft.desafioapi.model.Fornecedor;

public class CustomProdutoSerializer extends StdSerializer<Fornecedor> {

	private static final long serialVersionUID = 1057276000702591921L;

	public CustomProdutoSerializer() {
		this(null);
	}

	public CustomProdutoSerializer(Class<Fornecedor> t) {
		super(t);
	}

	@Override
	public void serialize(Fornecedor fornecedor, JsonGenerator generator, SerializerProvider provider)
			throws IOException {

		generator.writeObject(Map.of("id", fornecedor.getId()));

	}

}
