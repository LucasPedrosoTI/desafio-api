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

import com.gft.desafioapi.converter.ClienteConverter;
import com.gft.desafioapi.dto.cliente.ClienteDTOResponse;
import com.gft.desafioapi.dto.cliente.ClienteDTORequest;
import com.gft.desafioapi.event.RecursoCriadoEvent;
import com.gft.desafioapi.model.Cliente;
import com.gft.desafioapi.repository.ClienteRepository;
import com.gft.desafioapi.repository.filter.ClienteFilter;
import com.gft.desafioapi.service.ClienteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Clientes")
@RestController
@RequestMapping("/api/clientes")
public class ClienteResource {

	@Autowired
	ClienteRepository clienteRepository;

	@Autowired
	ClienteService clienteService;

	@Autowired
	ClienteConverter converter;

	@Autowired
	ApplicationEventPublisher publisher;

	Class<ClienteResource> resource = ClienteResource.class;

	@Cacheable(value = "custom-cache", key = "'ClientesInCache'+#filter")
	@ApiOperation("Lista todos os clientes")
	@GetMapping
	public Page<ClienteDTOResponse> listarClientes(ClienteFilter filter, Pageable pageable) {

		Page<Cliente> clientes = clienteService.pesquisarClientes(filter, pageable);

		return createSelfLink(converter.entityToDtoResponse(clientes), resource);
	}

	@CacheEvict(value = "custom-cache", key = "'ClienteInCache'+#id", condition = "#id == null")
	@Cacheable(value = "custom-cache", key = "'ClienteInCache'+#id", condition = "#id != null")
	@ApiOperation("Retorna um cliente por ID")
	@GetMapping("/{id}")
	public ClienteDTOResponse encontrarClientePorId(@PathVariable Long id) {
		Cliente cliente = clienteService.findClienteById(id);
		return createRelListAllLink(converter.entityToDtoResponse(cliente), resource);
	}

	@ApiOperation("Cadastra um novo cliente")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public ClienteDTOResponse criarCliente(@RequestBody @Valid ClienteDTORequest dto, HttpServletResponse response) {

		Cliente cliente = clienteService.create(converter.dtoRequestToEntity(dto));

		publisher.publishEvent(new RecursoCriadoEvent(this, response, cliente.getId()));

		return createSelfLink(converter.entityToDtoResponse(cliente), resource);
	}

	@ApiOperation("Atualiza os dados de um cliente por ID")
	@PutMapping("/{id}")
	public ClienteDTOResponse atualizarCliente(@PathVariable Long id, @RequestBody ClienteDTORequest dto) {

		Cliente cliente = clienteService.update(id, converter.dtoRequestToEntity(dto));

		return converter.entityToDtoResponse(cliente);
	}

	@ApiOperation("Exclui um cliente por ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> excluirCliente(@PathVariable Long id) {
		return clienteService.delete(id);
	}

	@ApiOperation("Lista os clientes em ordem alfabética crescente por nome")
	@GetMapping("/asc")
	public Page<ClienteDTOResponse> listarClientesAsc(Pageable pageable) {

		return createSelfLink(converter.entityToDtoResponse(clienteRepository.findAllOrderByNomeAsc(pageable)), resource);
	}

	@ApiOperation("Lista os clientes em ordem alfabética decrescente por nome")
	@GetMapping("/desc")
	public Page<ClienteDTOResponse> listarClientesDesc(Pageable pageable) {
		return createSelfLink(converter.entityToDtoResponse(clienteRepository.findAllOrderByNomeDesc(pageable)), resource);
	}

	@ApiOperation("Busca clientes por nome")
	@GetMapping("/nome/{nome}")
	public Page<ClienteDTOResponse> encontrarClientesPorNome(@PathVariable String nome, Pageable pageable) {
		return createSelfLink(converter.entityToDtoResponse(clienteRepository.findByNomeContaining(nome, pageable)), resource);
	}


}
