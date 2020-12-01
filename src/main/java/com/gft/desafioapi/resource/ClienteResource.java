package com.gft.desafioapi.resource;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.gft.desafioapi.service.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteResource {

	@Autowired
	ClienteRepository clienteRepository;

	@Autowired
	ClienteService clienteService;

	@GetMapping
	public Page<Cliente> listAll(Pageable pageable) {
		return clienteRepository.findAll(pageable);
	}

	@GetMapping("/{id}")
	public Cliente findById(@PathVariable Long id) {
		return clienteService.findClienteById(id);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public Cliente create(@RequestBody @Valid Cliente cliente) {
		return clienteService.create(cliente);
	}

	@PutMapping("/{id}")
	public Cliente update(@PathVariable Long id, @RequestBody Cliente cliente) {
		return clienteService.update(id, cliente);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> delete(@PathVariable Long id) {
		return clienteService.delete(id);
	}

	@GetMapping("/asc")
	public Page<Cliente> listAllAsc(Pageable pageable) {
		return clienteRepository.findAllOrderByNomeAsc(pageable);
	}

	@GetMapping("/desc")
	public Page<Cliente> listAllDesc(Pageable pageable) {
		return clienteRepository.findAllOrderByNomeDesc(pageable);
	}

	@GetMapping("/nome/{nome}")
	public Page<Cliente> listByNome(@PathVariable String nome, Pageable pageable) {
		return clienteRepository.findByNomeContaining(nome, pageable);
	}

}
