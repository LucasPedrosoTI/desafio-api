package com.gft.desafioapi.service;

import static com.gft.desafioapi.utils.TestUtils.assertExceptionAndMessage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.gft.desafioapi.model.Fornecedor;
import com.gft.desafioapi.model.Produto;
import com.gft.desafioapi.repository.ProdutoRepository;
import com.gft.desafioapi.repository.filter.ProdutoFilter;
import com.gft.desafioapi.utils.ApiUtils;
import com.gft.desafioapi.utils.Constants;

class ProdutoServiceTest {

	@Mock
	private Pageable pageable;

	@Mock
	private ProdutoRepository produtoRepository;

	@Mock
	private FornecedorService fornecedorService;

	@InjectMocks
	private ProdutoService produtoService;

	private Produto produto;
	private Fornecedor fornecedor;

	@BeforeEach
	void setup() throws Exception {
		fornecedor = Fornecedor.builder()
				.withId(1L)
				.withNome("Nome")
				.withCnpj("12345678912345")
				.build();

		produto = Produto.builder()
				.withId(1L)
				.withNome("iPhone")
				.withValor(BigDecimal.valueOf(1000))
				.withFornecedor(fornecedor)
				.withPromocao(false)
				.build();

		MockitoAnnotations.openMocks(this);
	}

	@Test
	void deveLancarErroSeFornecedorNaoInformado() throws Exception {
		produto.setFornecedor(null);

		assertExceptionAndMessage(EmptyResultDataAccessException.class, Constants.FORNECEDOR_INEXISTENTE,
				() -> produtoService.create(produto));
	}

	@Test
	void deveLancarErroSeProdutoNaoEstaEmPromocaoEValorPromoForDiferenteDeNull() throws Exception {
		produto.setValorPromo(BigDecimal.valueOf(500));

		assertExceptionAndMessage(DataIntegrityViolationException.class, Constants.PRODUTO_VALOR_PROMO_MESSAGE,
				() -> produtoService.create(produto));
	}

	@Test
	void deveLancarErroSeProdutoEstaEmPromocaoEValorPromoForNull() throws Exception {
		produto.setPromocao(true);

		assertExceptionAndMessage(DataIntegrityViolationException.class, Constants.PRODUTO_VALOR_PROMO_MESSAGE,
				() -> produtoService.create(produto));
	}



	@Test
	void deveCriarUmProdutoQueNaoEstaEmPromocao() throws Exception {

		mockFindFornecedorById(produto.getFornecedor().getId());

		produtoService.create(produto);

		produto.setId(null);

		verify(produtoRepository).save(produto);
	}

	@Test
	void deveCriarProdutoEmPromocao() throws Exception {
		produto.setPromocao(true);
		produto.setValorPromo(BigDecimal.valueOf(500));

		mockFindFornecedorById(produto.getFornecedor().getId());

		produtoService.create(produto);

		produto.setId(null);

		verify(produtoRepository).save(produto);
	}



	@Test
	void deveAtualizarUmProduto() throws Exception {
		String novoNome = "iPhone 12";
		Produto atualizacao = Produto.builder()
				.withNome(novoNome)
				.build();

		mockFindProdutoById();

		produtoService.update(1L, atualizacao);

		produto.setNome(novoNome);

		verify(produtoRepository).save(produto);
	}


	@Test
	void deveRetornarMapSuccessTrueAoExcluir() throws Exception {

		doNothing().when(produtoRepository).deleteById(1L);

		assertEquals(Constants.MAP_SUCCESS_TRUE, produtoService.delete(1L));

	}

	@Test
	void deveNormalizarFilterDaPesquisa() throws Exception {

		ProdutoFilter filter = new ProdutoFilter();

		produtoService.pesquisarProdutos(filter, pageable);

		verify(produtoRepository).pesquisarProdutos(
				"",
				"",
				BigDecimal.ZERO,
				Constants.MAX_DECIMAL,
				0L,
				Constants.MAX_DECIMAL.longValue(),
				BigDecimal.ZERO,
				Constants.MAX_DECIMAL,
				pageable);
	}

	@Test
	void deveSalvarUmaImagem() throws Exception {
		MultipartFile imagem = new MockMultipartFile("name", "originalName", "jpeg", RandomUtils.nextBytes(10));
		MockedStatic<ApiUtils> utils = mockStatic(ApiUtils.class);

		utils.when(ApiUtils::getRandomString).thenReturn("test");

		mockFindProdutoById();

		produtoService.salvarImagem(imagem, 1L);

		produto.setImagem("test_originalName");

		verify(produtoRepository).save(produto);
		utils.close();
	}



	private void mockFindFornecedorById(Long fornecedorId) {
		when(fornecedorService.findFornecedorById(fornecedorId)).thenReturn(fornecedor);
	}

	private void mockFindProdutoById() {
		when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
	}

}
