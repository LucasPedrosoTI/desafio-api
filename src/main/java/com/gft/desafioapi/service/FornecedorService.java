package com.gft.desafioapi.service;

import static com.gft.desafioapi.utils.Coalesce.coalesce;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gft.desafioapi.model.Fornecedor;
import com.gft.desafioapi.model.Produto;
import com.gft.desafioapi.repository.FornecedorRepository;

@Service
public class FornecedorService {

	@Autowired
	FornecedorRepository fornecedorRepository;

	public Fornecedor findFornecedorById(Long id) {
		return fornecedorRepository.findById(id).orElseThrow(() -> {
			throw new EmptyResultDataAccessException(1);
		});
	}

	public Fornecedor update(Long id, Fornecedor fornecedor) {
		Fornecedor fornecedorExistente = findFornecedorById(id);

		String nome = coalesce(fornecedor.getNome(), fornecedorExistente.getNome());
		String cnpj = coalesce(fornecedor.getCnpj(), fornecedorExistente.getCnpj());
		List<Produto> produtos = fornecedorExistente.getProdutos();

		return fornecedorRepository.save(new Fornecedor(id, nome, cnpj, produtos));

	}
}
