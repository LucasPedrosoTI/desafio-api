package com.gft.desafioapi.service;

import java.math.BigDecimal;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gft.desafioapi.model.Produto;
import com.gft.desafioapi.model.Venda;
import com.gft.desafioapi.repository.VendaRepository;
import com.gft.desafioapi.utils.EntityUtils;

@Service
public class VendaService {

	@Autowired
	VendaRepository vendaRepository;

	public Venda findProdutoById(Long id) {
		return vendaRepository.findById(id).orElseThrow(() -> {
			throw new EmptyResultDataAccessException(1);
		});
	}

	public Venda create(Venda venda) {
		EntityUtils.setIdNull(venda);

		venda.setTotalCompra(calcularTotalCompra(venda));

		return vendaRepository.save(venda);
	}

	private BigDecimal calcularTotalCompra(Venda venda) {

		if (Objects.isNull(venda.getProdutos())) {
			throw new EmptyResultDataAccessException("É obrigatório fornecer uma lista de produtos", 1);
		}

		Double totalCompra = 0d;

		for (Produto produto : venda.getProdutos()) {
			if (produto.isPromocao()) {
				totalCompra += produto.getValorPromo().doubleValue();
			} else {
				totalCompra += produto.getValor().doubleValue();
			}
		}

		return new BigDecimal(totalCompra);
	}

}
