package com.gft.desafioapi.resource;

import static com.gft.desafioapi.utils.ApiUtils.createRelListAllLink;
import static com.gft.desafioapi.utils.ApiUtils.createSelfLink;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gft.desafioapi.converter.ProdutoConverter;
import com.gft.desafioapi.dto.produto.ProdutoDTOResponse;
import com.gft.desafioapi.dto.produto.ProdutoDTORequest;
import com.gft.desafioapi.event.RecursoCriadoEvent;
import com.gft.desafioapi.model.Produto;
import com.gft.desafioapi.repository.ProdutoRepository;
import com.gft.desafioapi.repository.filter.ProdutoFilter;
import com.gft.desafioapi.service.ProdutoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Produtos")
@RestController
@RequestMapping("/api/produtos")
public class ProdutoResource {

	@Autowired
	ProdutoRepository produtoRepository;

	@Autowired
	ProdutoService produtoService;

	@Autowired
	ProdutoConverter converter;

	@Autowired
	ApplicationEventPublisher publisher;

	Class<ProdutoResource> resource = ProdutoResource.class;

	@Cacheable(value = "custom-cache", key = "'ProdutosInCache'+#filter")
	@ApiOperation("Lista todos os produtos")
	@GetMapping
	public Page<ProdutoDTOResponse> listarProdutos(
			ProdutoFilter filter,
			Pageable pageable) {
		return createSelfLink(converter.entityToDtoResponse(produtoService.pesquisarProdutos(filter, pageable)), resource);
	}

	@CacheEvict(value = "custom-cache", key = "'ProdutoInCache'+#id", condition = "#id == null")
	@Cacheable(value = "custom-cache", key = "'ProdutoInCache'+#id", condition = "#id != null")
	@ApiOperation("Retorna um produto por ID")
	@GetMapping("/{id}")
	public ProdutoDTOResponse encontrarProdutoPorId(@PathVariable Long id) {
		return createRelListAllLink(converter.entityToDtoResponse(produtoService.findProdutoById(id)), resource);
	}

	@ApiOperation("Cadastra um novo produto")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public ProdutoDTOResponse criarProduto(
			@RequestBody @Valid ProdutoDTORequest dto,
			HttpServletResponse response) {

		Produto produtoSalvo = produtoService.create(converter.dtoRequestToEntity(dto));

		publisher.publishEvent(new RecursoCriadoEvent(this, response, produtoSalvo.getId()));

		return createSelfLink(converter.entityToDtoResponse(produtoSalvo), resource);
	}

	@ApiOperation("Atualiza os dados de um produto por ID")
	@PutMapping("/{id}")
	public ProdutoDTOResponse atualizarProduto(@PathVariable Long id, @RequestBody ProdutoDTORequest dto) {

		Produto produto = produtoService.update(id, converter.dtoRequestToEntity(dto));

		return converter.entityToDtoResponse(produto);

	}

	@ApiOperation("Exclui um produto por ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> excluirProduto(@PathVariable Long id) {
		return produtoService.delete(id);
	}

	@ApiOperation("Lista os produtos em ordem alfabética crescente por nome")
	@GetMapping("/asc")
	public Page<ProdutoDTOResponse> listarProdutosAsc(Pageable pageable) {
		return createSelfLink(converter.entityToDtoResponse(produtoRepository.findAllOrderByNomeAsc(pageable)), resource);
	}

	@ApiOperation("Lista os produtos em ordem alfabética decrescente por nome")
	@GetMapping("/desc")
	public Page<ProdutoDTOResponse> listarProdutosDesc(Pageable pageable) {
		return createSelfLink(converter.entityToDtoResponse(produtoRepository.findAllOrderByNomeDesc(pageable)), resource);
	}

	@ApiOperation("Busca produtos por nome")
	@GetMapping("/nome/{nome}")
	public Page<ProdutoDTOResponse> encontrarProdutoPorNome(@PathVariable String nome, Pageable pageable) {
		return createSelfLink(converter.entityToDtoResponse(produtoRepository.findByNomeContaining(nome, pageable)), resource);
	}

	@ApiOperation("Faz o upload de uma imagem")
	@PutMapping(path = "/{id}/imagem", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> uploadImagem(@RequestPart MultipartFile imagem, @PathVariable Long id)
			throws IOException {
		try {

			ProdutoDTOResponse dto = converter.entityToDtoResponse(produtoService.salvarImagem(imagem, id));

			return ResponseEntity.ok(createSelfLink(dto, resource));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(Map.of("mensagemUsuario", "Operação não permitida", "mensagemDev",
							ExceptionUtils.getRootCauseMessage(e)));
		}
	}

}
