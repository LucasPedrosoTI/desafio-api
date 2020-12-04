package com.gft.desafioapi.service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gft.desafioapi.model.Venda;
import com.gft.desafioapi.repository.VendaRepository;
import com.gft.desafioapi.utils.EntityUtils;

@Service
public class VendaService {

	@Autowired
	VendaRepository vendaRepository;

	public Venda findVendaById(Long id) {
		return vendaRepository.findById(id).orElseThrow(() -> {
			throw new EmptyResultDataAccessException(1);
		});
	}

	public Venda create(Venda venda) {
		EntityUtils.setIdNull(venda);

		checkFornecedor(venda);

		validarSeProdutosPertencemAoFornecedor(venda);

		venda.setTotalCompra(calcularTotalCompra(venda));

		return vendaRepository.save(venda);
	}

	public Venda update(Long id, Venda venda) {
		Venda vendaAtualizada = venda.coalesce(findVendaById(id), id);

		validarSeProdutosPertencemAoFornecedor(vendaAtualizada);

		vendaAtualizada.setTotalCompra(calcularTotalCompra(vendaAtualizada));

		return vendaRepository.save(vendaAtualizada);
	}

	public ResponseEntity<Map<String, Boolean>> delete(Long id) {
		vendaRepository.deleteById(id);
		return new ResponseEntity<Map<String, Boolean>>(Map.of("success", true), HttpStatus.OK);
	}

	private void validarSeProdutosPertencemAoFornecedor(Venda venda) {
		venda.getProdutos().forEach(produto -> {
			if (!produto.getFornecedor().getId().equals(venda.getFornecedor().getId())) {
				throw new DataIntegrityViolationException("Os produtos devem ser do mesmo fornecedor informado");
			}
		});
	}

	private BigDecimal calcularTotalCompra(Venda venda) {

		if (Objects.isNull(venda.getProdutos())) {
			throw new EmptyResultDataAccessException("É obrigatório fornecer uma lista de produtos", 1);
		}
		// @formatter:off
		return venda.getProdutos()
				.stream()
				.map(p -> p.isPromocao() ? p.getValorPromo() : p.getValor())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		// @formatter:on
	}

	private void checkFornecedor(Venda venda) {
		if (Objects.isNull(venda.getFornecedor())) {
			throw new EmptyResultDataAccessException("Fornecedor não informado", 1);
		}
	}



}
