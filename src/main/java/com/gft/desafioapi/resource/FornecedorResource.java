package com.gft.desafioapi.resource;

import static com.gft.desafioapi.utils.ApiUtils.createRelListAllLink;
import static com.gft.desafioapi.utils.ApiUtils.createSelfLink;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gft.desafioapi.converter.FornecedorConverter;
import com.gft.desafioapi.dto.FornecedorDTO;
import com.gft.desafioapi.event.RecursoCriadoEvent;
import com.gft.desafioapi.model.Fornecedor;
import com.gft.desafioapi.repository.FornecedorRepository;
import com.gft.desafioapi.repository.filter.FornecedorFilter;
import com.gft.desafioapi.service.FornecedorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Fornecedores")
@RestController
@RequestMapping("/api/fornecedores")
public class FornecedorResource {

	@Autowired
	FornecedorRepository fornecedorRepository;

	@Autowired
	FornecedorService fornecedorService;

	@Autowired
	FornecedorConverter converter;

	@Autowired
	ApplicationEventPublisher publisher;

	Class<FornecedorResource> resource = FornecedorResource.class;

	@Cacheable(value = "custom-cache", key = "'FornecedoresInCache'+#filter")
	@ApiOperation("Lista todos os fornecedores")
	@GetMapping
	public Page<Object> listarFornecedores(FornecedorFilter filter, Pageable pageable) {
		return createSelfLink(converter.entityToDto(fornecedorService.findAllWithFilter(filter, pageable)), resource);
	}

	@CacheEvict(value = "custom-cache", key = "'FornecedorInCache'+#id", condition = "#id == null")
	@Cacheable(value = "custom-cache", key = "'FornecedorInCache'+#id", condition = "#id != null")
	@ApiOperation("Retorna um fornecedor por ID")
	@GetMapping("/{id}")
	public Object encontrarFornecedorPorId(@PathVariable Long id) {
		return createRelListAllLink(converter.entityToDto(fornecedorService.findFornecedorById(id)), resource);
	}

	@ApiOperation("Cadastra um novo fornecedor")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public FornecedorDTO criarFornecedor(@RequestBody @Valid FornecedorDTO dto, HttpServletResponse response) {

		Fornecedor fornecedor = fornecedorService.create(converter.dtoToEntity(dto));

		publisher.publishEvent(new RecursoCriadoEvent(this, response, fornecedor.getId()));

		return converter.entityToDto(fornecedor);
	}

	@ApiOperation("Atualiza os dados de um fornecedor por ID")
	@PutMapping("/{id}")
	public Object atualizarFornecedor(@PathVariable Long id, @RequestBody FornecedorDTO dto) {

		Fornecedor fornecedor = fornecedorService.update(id, converter.dtoToEntity(dto));

		return createSelfLink(converter.entityToDto(fornecedor), resource);
	}

	@ApiOperation("Exclui um fornecedor por ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> excluirFornecedor(@PathVariable Long id) {

		return fornecedorService.delete(id);
	}

	@ApiOperation("Lista os fornecedores em ordem alfabética crescente por nome")
	@GetMapping("/asc")
	public Page<Object> listarFornecedoresAsc(Pageable pageable) {
		return createSelfLink(converter.entityToDto(fornecedorRepository.findAllOrderByNomeAsc(pageable)), resource);
	}

	@ApiOperation("Lista os fornecedores em ordem alfabética decrescente por nome")
	@GetMapping("/desc")
	public Page<Object> listarFornecedoresDesc(Pageable pageable) {
		return createSelfLink(converter.entityToDto(fornecedorRepository.findAllOrderByNomeDesc(pageable)), resource);
	}

	@ApiOperation("Busca fornecedores por nome")
	@GetMapping("/nome/{nome}")
	public Page<Object> encontrarFornecedoresPorNome(@PathVariable String nome, Pageable pageable) {
		return createSelfLink(converter.entityToDto(fornecedorRepository.findByNomeContaining(nome, pageable)),
				resource);
	}

}
