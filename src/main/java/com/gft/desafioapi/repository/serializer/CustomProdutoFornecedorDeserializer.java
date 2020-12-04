package com.gft.desafioapi.repository.serializer;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.gft.desafioapi.model.Fornecedor;
import com.gft.desafioapi.repository.FornecedorRepository;

@Component
public class CustomProdutoFornecedorDeserializer extends StdDeserializer<Fornecedor> {

	@Autowired
	FornecedorRepository fornecedorRepository;

	private static final long serialVersionUID = -839983745171929649L;

	public CustomProdutoFornecedorDeserializer() {
		this(null);
	}

	public CustomProdutoFornecedorDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public Fornecedor deserialize(JsonParser jsonparser, DeserializationContext context)
			throws IOException, JsonProcessingException {

		Long id = null;
		String cnpj = null;
		JsonNode node = jsonparser.getCodec().readTree(jsonparser);

		if (!node.has("id") && !node.has("cnpj")) {
			throw new EmptyResultDataAccessException("Fornecedor não passado na requisição", 1);
		}

		if (node.has("id")) {
			id = node.get("id").asLong();
		}

		if (node.has("cnpj")) {
			cnpj = node.get("cnpj").asText();
		}

		Example<Fornecedor> fornecedorExample = Example.of(new Fornecedor(id, null, cnpj, null));

		return fornecedorRepository.findOne(fornecedorExample).orElseThrow(() -> {
			throw new EmptyResultDataAccessException(1);
		});
	}

}
