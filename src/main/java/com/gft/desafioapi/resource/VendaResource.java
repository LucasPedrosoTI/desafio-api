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

import com.gft.desafioapi.event.RecursoCriadoEvent;
import com.gft.desafioapi.model.Venda;
import com.gft.desafioapi.repository.VendaRepository;
import com.gft.desafioapi.repository.filter.VendaFilter;
import com.gft.desafioapi.service.VendaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Vendas")
@RestController
@RequestMapping("/api/vendas")
public class VendaResource {

	@Autowired
	VendaRepository vendaRepository;

	@Autowired
	VendaService vendaService;

	@Autowired
	ApplicationEventPublisher publisher;

	@Cacheable(value = "custom-cache", key = "'VendasInCache'+#filter")
	@ApiOperation("Lista todas as vendas")
	@GetMapping
	public Page<Venda> listarVendas(VendaFilter filter, Pageable pageable) {
		return vendaService.findAllWithFilter(filter, pageable);
	}

	@CacheEvict(value = "custom-cache", key = "'VendaInCache'+#id", condition = "#id == null")
	@Cacheable(value = "custom-cache", key = "'VendaInCache'+#id", condition = "#id != null")
	@ApiOperation("Retorna uma venda por ID")
	@GetMapping("/{id}")
	public Venda encontrarVendaPorId(@PathVariable Long id) {
		return vendaService.findVendaById(id);
	}

	@ApiOperation("Cadastra uma nova venda")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public Venda criarVenda(@RequestBody @Valid Venda venda, HttpServletResponse response) {
		Venda vendaCriada = vendaService.create(venda);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, vendaCriada.getId()));
		return vendaCriada;
	}

	@ApiOperation("Atualiza os dados de uma venda por ID")
	@PutMapping("/{id}")
	public Venda atualizarVenda(@PathVariable Long id, @RequestBody Venda venda) {
		return vendaService.update(id, venda);
	}

	@ApiOperation("Exclui uma venda por ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> excluirVenda(@PathVariable Long id) {
		return vendaService.delete(id);
	}

}
