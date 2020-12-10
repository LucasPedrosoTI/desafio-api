package com.gft.desafioapi.repository.serializer;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.gft.desafioapi.model.Fornecedor;

public class CustomProdutoFornecedorSerializer extends StdSerializer<Fornecedor> {

	private static final long serialVersionUID = 1057276000702591921L;

	public CustomProdutoFornecedorSerializer() {
		this(null);
	}

	public CustomProdutoFornecedorSerializer(
			Class<Fornecedor> t) {
		super(t);
	}

	@Override
	public void serialize(Fornecedor fornecedor, JsonGenerator generator, SerializerProvider provider)
			throws IOException {

		generator.writeObject(Map.of("id", fornecedor.getId()));

	}

}
