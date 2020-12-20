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
import com.gft.desafioapi.dto.fornecedor.FornecedorDTOResponse;
import com.gft.desafioapi.dto.fornecedor.FornecedorDTORequest;
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
	public Page<FornecedorDTOResponse> listarFornecedores(FornecedorFilter filter, Pageable pageable) {
		return createSelfLink(converter.entityToDtoResponse(fornecedorService.pesquisarFornecedores(filter, pageable)), resource);
	}

	@CacheEvict(value = "custom-cache", key = "'FornecedorInCache'+#id", condition = "#id == null")
	@Cacheable(value = "custom-cache", key = "'FornecedorInCache'+#id", condition = "#id != null")
	@ApiOperation("Retorna um fornecedor por ID")
	@GetMapping("/{id}")
	public FornecedorDTOResponse encontrarFornecedorPorId(@PathVariable Long id) {
		return createRelListAllLink(converter.entityToDtoResponse(fornecedorService.findFornecedorById(id)), resource);
	}

	@ApiOperation("Cadastra um novo fornecedor")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public FornecedorDTOResponse criarFornecedor(@RequestBody @Valid FornecedorDTORequest dto, HttpServletResponse response) {

		Fornecedor fornecedor = fornecedorService.create(converter.dtoRequestToEntity(dto));

		publisher.publishEvent(new RecursoCriadoEvent(this, response, fornecedor.getId()));

		return createSelfLink(converter.entityToDtoResponse(fornecedor), resource);
	}

	@ApiOperation("Atualiza os dados de um fornecedor por ID")
	@PutMapping("/{id}")
	public FornecedorDTOResponse atualizarFornecedor(@PathVariable Long id, @RequestBody FornecedorDTORequest dto) {

		Fornecedor fornecedor = fornecedorService.update(id, converter.dtoRequestToEntity(dto));

		return createSelfLink(converter.entityToDtoResponse(fornecedor), resource);
	}

	@ApiOperation("Exclui um fornecedor por ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> excluirFornecedor(@PathVariable Long id) {

		return fornecedorService.delete(id);
	}

	@ApiOperation("Lista os fornecedores em ordem alfabética crescente por nome")
	@GetMapping("/asc")
	public Page<FornecedorDTOResponse> listarFornecedoresAsc(Pageable pageable) {
		return createSelfLink(converter.entityToDtoResponse(fornecedorRepository.findAllOrderByNomeAsc(pageable)), resource);
	}

	@ApiOperation("Lista os fornecedores em ordem alfabética decrescente por nome")
	@GetMapping("/desc")
	public Page<FornecedorDTOResponse> listarFornecedoresDesc(Pageable pageable) {
		return createSelfLink(converter.entityToDtoResponse(fornecedorRepository.findAllOrderByNomeDesc(pageable)), resource);
	}

	@ApiOperation("Busca fornecedores por nome")
	@GetMapping("/nome/{nome}")
	public Page<FornecedorDTOResponse> encontrarFornecedoresPorNome(@PathVariable String nome, Pageable pageable) {
		return createSelfLink(converter.entityToDtoResponse(fornecedorRepository.findByNomeContaining(nome, pageable)),
				resource);
	}

}
