package com.gft.desafioapi.resource;

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
import com.gft.desafioapi.dto.ClienteDTO;
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

	@Cacheable(value = "custom-cache", key = "'ClientesInCache'+#filter")
	@ApiOperation("Lista todos os clientes")
	@GetMapping
	public Page<ClienteDTO> listarClientes(ClienteFilter filter, Pageable pageable) {

		Page<Cliente> clientes = clienteService.pesquisarClientes(filter, pageable);

		return converter.entityToDto(clientes);
	}

	@CacheEvict(value = "custom-cache", key = "'ClienteInCache'+#id", condition = "#id == null")
	@Cacheable(value = "custom-cache", key = "'ClienteInCache'+#id", condition = "#id != null")
	@ApiOperation("Retorna um cliente por ID")
	@GetMapping("/{id}")
	public ClienteDTO encontrarClientePorId(@PathVariable Long id) {
		Cliente cliente = clienteService.findClienteById(id);
		return converter.entityToDto(cliente);
	}

	@ApiOperation("Cadastra um novo cliente")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public ClienteDTO criarCliente(@RequestBody @Valid ClienteDTO dto, HttpServletResponse response) {

		Cliente cliente = clienteService.create(converter.dtoToEntity(dto));

		publisher.publishEvent(new RecursoCriadoEvent(this, response, cliente.getId()));

		return converter.entityToDto(cliente);
	}

	@ApiOperation("Atualiza os dados de um cliente por ID")
	@PutMapping("/{id}")
	public ClienteDTO atualizarCliente(@PathVariable Long id, @RequestBody ClienteDTO dto) {

		Cliente cliente = clienteService.update(id, converter.dtoToEntity(dto));

		return converter.entityToDto(cliente);
	}

	@ApiOperation("Exclui um cliente por ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> excluirCliente(@PathVariable Long id) {
		return clienteService.delete(id);
	}

	@ApiOperation("Lista os clientes em ordem alfabética crescente por nome")
	@GetMapping("/asc")
	public Page<ClienteDTO> listarClientesAsc(Pageable pageable) {

		return converter.entityToDto(clienteRepository.findAllOrderByNomeAsc(pageable));
	}

	@ApiOperation("Lista os clientes em ordem alfabética decrescente por nome")
	@GetMapping("/desc")
	public Page<ClienteDTO> listarClientesDesc(Pageable pageable) {
		return converter.entityToDto(clienteRepository.findAllOrderByNomeDesc(pageable));
	}

	@ApiOperation("Busca clientes por nome")
	@GetMapping("/nome/{nome}")
	public Page<ClienteDTO> encontrarClientesPorNome(@PathVariable String nome, Pageable pageable) {
		return converter.entityToDto(clienteRepository.findByNomeContaining(nome, pageable));
	}

}
