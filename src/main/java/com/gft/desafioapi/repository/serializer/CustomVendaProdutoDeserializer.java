package com.gft.desafioapi.repository.serializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.gft.desafioapi.model.Produto;
import com.gft.desafioapi.repository.ProdutoRepository;

@Component
public class CustomVendaProdutoDeserializer extends StdDeserializer<List<Produto>> {

	@Autowired
	ProdutoRepository produtoRepository;

	private static final long serialVersionUID = -839983745171929649L;

	public CustomVendaProdutoDeserializer() {
		this(null);
	}

	public CustomVendaProdutoDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public List<Produto> deserialize(JsonParser jsonparser, DeserializationContext context)
			throws IOException, JsonProcessingException {

		List<Produto> produtos = new ArrayList<>();
		JsonNode node = jsonparser.getCodec().readTree(jsonparser);

		if (node.size() == 0) {
			throw new EmptyResultDataAccessException("Produtos não passados na requisição", 1);
		}

		node.elements().forEachRemaining(element -> {

			if (!element.has("id")) {
				throw new EmptyResultDataAccessException("Deve ser passado o ID do produto", 1);
			}

			Long id = element.get("id").asLong();

			Produto produto = produtoRepository.findById(id).orElseThrow(() -> {
				throw new EmptyResultDataAccessException("Produto inexistente", 1);
			});

			produtos.add(produto);
		});

		return produtos;
	}

}
