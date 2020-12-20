package com.gft.desafioapi.service;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gft.desafioapi.model.Fornecedor;
import com.gft.desafioapi.repository.FornecedorRepository;
import com.gft.desafioapi.repository.filter.FornecedorFilter;
import com.gft.desafioapi.utils.ApiUtils;
import com.gft.desafioapi.utils.Constants;

@Service
public class FornecedorService {

	@Autowired
	FornecedorRepository fornecedorRepository;

	public Page<Fornecedor> pesquisarFornecedores(FornecedorFilter filter, Pageable pageable) {

		return fornecedorRepository.findByNomeContainingAndCnpjContaining(
				filter.getNome(),
				filter.getCnpj(),
				pageable);
	}

	public Fornecedor findFornecedorById(Long id) {
		return fornecedorRepository.findById(id).orElseThrow(() -> {
			throw new EmptyResultDataAccessException(1);
		});
	}

	public Fornecedor update(Long id, @Valid Fornecedor fornecedor) {

		Fornecedor fornecedorAtualizado = fornecedor.coalesce(findFornecedorById(id), id);

		return fornecedorRepository.save(fornecedorAtualizado);

	}

	public ResponseEntity<Map<String, Boolean>> delete(Long id) {
		fornecedorRepository.deleteById(id);

		return Constants.MAP_SUCCESS_TRUE;

	}

	public Fornecedor create(@Valid Fornecedor fornecedor) {
		ApiUtils.setIdNull(fornecedor);

		return fornecedorRepository.save(fornecedor);
	}

}
