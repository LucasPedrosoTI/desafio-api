package com.gft.desafioapi.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

	@PostMapping
	public Cliente create(@RequestBody @Valid Cliente cliente) {
		return clienteService.create(cliente);
	}

	@PutMapping("/{id}")
	public Cliente update(@PathVariable Long id, @RequestBody Cliente cliente) {
		return clienteService.update(id, cliente);
	}

}
