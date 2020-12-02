package com.gft.desafioapi.service;

import static com.gft.desafioapi.utils.EntityUtils.coalesce;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gft.desafioapi.model.Fornecedor;
import com.gft.desafioapi.model.Produto;
import com.gft.desafioapi.repository.FornecedorRepository;
import com.gft.desafioapi.repository.filter.FornecedorFilter;
import com.gft.desafioapi.utils.EntityUtils;

@Service
public class FornecedorService {

	@Autowired
	FornecedorRepository fornecedorRepository;

	public Page<Fornecedor> findAllWithFilter(FornecedorFilter filter, Pageable pageable) {

		String nome = "", cnpj = "";

		if (Objects.nonNull(filter)) {
			nome = coalesce(filter.getNome(), "");
			cnpj = coalesce(filter.getCnpj(), "");
		}

		return fornecedorRepository.findByNomeContainingAndCnpjContaining(nome, cnpj, pageable);
	}

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

	public ResponseEntity<Map<String, Boolean>> delete(Long id) {
		fornecedorRepository.deleteById(id);

		return new ResponseEntity<Map<String, Boolean>>(Map.of("success", true), HttpStatus.OK);

	}

	public Fornecedor salvar(@Valid Fornecedor fornecedor) {
		EntityUtils.setIdNull(fornecedor);

		return fornecedorRepository.save(fornecedor);
	}

}
