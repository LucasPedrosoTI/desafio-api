package com.gft.desafioapi.resource;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

	@Cacheable(value = "custom-cache", key = "'ClientesInCache'+#filter")
	@ApiOperation("Lista todos os clientes")
	@GetMapping
	public Page<Cliente> listarClientes(ClienteFilter filter, Pageable pageable) {
		return clienteService.pesquisarClientes(filter, pageable);
	}

	@CacheEvict(value = "custom-cache", key = "'ClienteInCache'+#id", condition = "#id == null")
	@Cacheable(value = "custom-cache", key = "'ClienteInCache'+#id", condition = "#id != null")
	@ApiOperation("Retorna um cliente por ID")
	@GetMapping("/{id}")
	public Cliente encontrarClientePorId(@PathVariable Long id) {
		return clienteService.findClienteById(id);
	}

	@ApiOperation("Cadastra um novo cliente")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public Cliente criarCliente(@RequestBody @Valid Cliente cliente) {
		return clienteService.criar(cliente);
	}

	@ApiOperation("Atualiza os dados de um cliente por ID")
	@PutMapping("/{id}")
	public Cliente atualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
		return clienteService.update(id, cliente);
	}

	@ApiOperation("Exclui um cliente por ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> excluirCliente(@PathVariable Long id) {
		return clienteService.delete(id);
	}

	@ApiOperation("Lista os clientes em ordem alfabética crescente por nome")
	@GetMapping("/asc")
	public Page<Cliente> listarClientesAsc(Pageable pageable) {
		return clienteRepository.findAllOrderByNomeAsc(pageable);
	}

	@ApiOperation("Lista os clientes em ordem alfabética decrescente por nome")
	@GetMapping("/desc")
	public Page<Cliente> listarClientesDesc(Pageable pageable) {
		return clienteRepository.findAllOrderByNomeDesc(pageable);
	}

	@ApiOperation("Busca clientes por nome")
	@GetMapping("/nome/{nome}")
	public Page<Cliente> encontrarClientesPorNome(@PathVariable String nome, Pageable pageable) {
		return clienteRepository.findByNomeContaining(nome, pageable);
	}

}
