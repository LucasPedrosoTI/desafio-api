package com.gft.desafioapi.service;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.gft.desafioapi.model.Produto;
import com.gft.desafioapi.repository.ProdutoRepository;
import com.gft.desafioapi.repository.filter.ProdutoFilter;
import com.gft.desafioapi.utils.ApiUtils;
import com.gft.desafioapi.utils.Constants;

@Service
public class ProdutoService {

	@Autowired
	ProdutoRepository produtoRepository;

	@Autowired
	FornecedorService fornecedorService;

	@Value("${app.host}")
	private String host;

	public Page<Produto> pesquisarProdutos(ProdutoFilter filter, Pageable pageable) {

		Page<Produto> produtos = produtoRepository.pesquisarProdutos(filter.getNome(), filter.getCodigoProduto(),
				filter.getValorDe(), filter.getValorAte(), filter.getQuantidadeDe(), filter.getQuantidadeAte(),
				filter.getValorPromoDe(), filter.getValorPromoAte(), pageable);

		serializeProdutoImagem(produtos);

		return produtos;
	}

	public Produto findProdutoById(Long id) {
		Produto produto = produtoRepository.findById(id).orElseThrow(() -> {
			throw new EmptyResultDataAccessException(1);
		});

		serializeProdutoImagem(produto);

		return produto;
	}

	public Produto create(Produto produto) {

		ApiUtils.setIdNull(produto);

		checkFornecedor(produto);

		validatePromocao(produto);

		fornecedorService.findFornecedorById(produto.getFornecedor().getId());

		Produto produtoSalvo = produtoRepository.save(produto);

		serializeProdutoImagem(produtoSalvo);

		return produtoSalvo;
	}

	public Produto update(Long id, @Valid Produto produto) {

		Produto produtoAtualizado = produto.coalesce(findProdutoById(id), id);

		validatePromocao(produtoAtualizado);

		Produto produtoSalvo = produtoRepository.save(produtoAtualizado);

		serializeProdutoImagem(produtoSalvo);

		return produtoSalvo;
	}

	public ResponseEntity<Map<String, Boolean>> delete(Long id) {
		this.produtoRepository.deleteById(id);

		return Constants.MAP_SUCCESS_TRUE;
	}

	public Produto salvarImagem(MultipartFile imagem, Long id) throws IOException {

		Produto produto = findProdutoById(id);

		String fileName = ApiUtils.getRandomString() + "_"
				+ StringUtils.cleanPath(Optional.ofNullable(imagem.getOriginalFilename()).orElse("no-name"));

		produto.setImagem(fileName);
		produto.setImagemBytes(imagem.getBytes());
		produto.setContentType(imagem.getContentType());

		Produto produtoSalvo = produtoRepository.save(produto);
		serializeProdutoImagem(produtoSalvo);

		return produtoSalvo;

	}

	private void validatePromocao(Produto produto) {
		if (Boolean.FALSE.equals(produto.isPromocao()) && Objects.nonNull(produto.getValorPromo())) {
			throw new DataIntegrityViolationException(Constants.PRODUTO_VALOR_PROMO_MESSAGE);
		}

		if (Boolean.TRUE.equals(produto.isPromocao()) && Objects.isNull(produto.getValorPromo())) {
			throw new DataIntegrityViolationException(Constants.PRODUTO_VALOR_PROMO_MESSAGE);
		}
	}

	private void checkFornecedor(Produto produto) {
		if (Objects.isNull(produto.getFornecedor())) {
			throw new EmptyResultDataAccessException(Constants.FORNECEDOR_INEXISTENTE, 1);
		}
	}

	private void serializeProdutoImagem(Produto produto) {
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("produtos/")
				.path(produto.getId().toString()).path("/imagem").toUriString();

		produto.setImagem(fileDownloadUri);
	}

	private void serializeProdutoImagem(Page<Produto> produtos) {
		produtos.forEach(this::serializeProdutoImagem);
	}

}
