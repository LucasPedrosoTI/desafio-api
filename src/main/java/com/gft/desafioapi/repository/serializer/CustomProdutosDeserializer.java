package com.gft.desafioapi.repository.serializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.gft.desafioapi.dto.AbstractDtoId;
import com.gft.desafioapi.repository.ProdutoRepository;
import com.gft.desafioapi.utils.Constants;

@Component
public class CustomProdutosDeserializer extends StdDeserializer<List<AbstractDtoId>> {

	@Autowired
	transient ProdutoRepository produtoRepository;

	private static final long serialVersionUID = -839983745171929649L;

	public CustomProdutosDeserializer() {
		this(null);
	}

	public CustomProdutosDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public List<AbstractDtoId> deserialize(JsonParser jsonparser, DeserializationContext context)
			throws IOException {

		List<AbstractDtoId> produtos = new ArrayList<>();
		JsonNode node = jsonparser.getCodec().readTree(jsonparser);

		if (node.size() == 0) {
			throw new EmptyResultDataAccessException(Constants.PRODUTO_INEXISTENTE, 1);
		}

		node.elements().forEachRemaining(element -> {

			if (!element.has("id")) {
				throw new EmptyResultDataAccessException(Constants.PRODUTO_INEXISTENTE, 1);
			}

			Long id = element.get("id").asLong();

					produtoRepository.findById(id)
							.orElseThrow(() -> {
				throw new EmptyResultDataAccessException(Constants.PRODUTO_INEXISTENTE, 1);
			});

					produtos.add(new AbstractDtoId(id));
		});

		return produtos;
	}

}
