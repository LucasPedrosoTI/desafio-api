package com.gft.desafioapi.service;

import static com.gft.desafioapi.utils.TestUtils.assertExceptionAndMessage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;

import com.gft.desafioapi.model.Fornecedor;
import com.gft.desafioapi.model.Produto;
import com.gft.desafioapi.model.Venda;
import com.gft.desafioapi.repository.VendaRepository;
import com.gft.desafioapi.repository.filter.VendaFilter;
import com.gft.desafioapi.utils.Constants;

class VendaServiceTest {

	@Mock
	private VendaRepository vendaRepository;

	@Mock
	private Pageable pageable;

	@InjectMocks
	private VendaService vendaService;

	private Venda venda;
	private Fornecedor fornecedor;
	private List<Produto> produtos = new ArrayList<>();

	private Produto produto1;
	private Produto produto2;
	private VendaFilter filter;

	@BeforeEach
	public void setup() throws Exception {
		filter = new VendaFilter();
		fornecedor = new Fornecedor(1L, "Apple", "12345678912345", new ArrayList<>());

		produto1 = Produto.builder()
				.withFornecedor(fornecedor)
				.withPromocao(false)
				.withValor(BigDecimal.valueOf(1000))
				.build();

		produto2 = Produto.builder()
				.withFornecedor(fornecedor)
				.withPromocao(true)
				.withValor(BigDecimal.valueOf(1000))
				.withValorPromo(BigDecimal.valueOf(500))
				.build();

		produtos.add(produto1);
		produtos.add(produto2);

		venda = new Venda(1L, null, LocalDate.now(), null, fornecedor, produtos);

		MockitoAnnotations.openMocks(this);
	}

	@Test
	void deveLancarErroSeNaoHouverFornecedor() throws Exception {
		venda.setFornecedor(null);

		assertExceptionAndMessage(EmptyResultDataAccessException.class, Constants.FORNECEDOR_INEXISTENTE,
				() -> vendaService.create(venda));
	}

	@Test
	void deveLancarErroSeNaoHouverProdutos() throws Exception {
		venda.setProdutos(null);

		assertExceptionAndMessage(EmptyResultDataAccessException.class, Constants.PRODUTO_INEXISTENTE,
				() -> vendaService.create(venda));
	}

	@Test
	void deveLancarErroSeProdutoForDeFornecedorDiferente() throws Exception {
		Fornecedor fornecedor2 = new Fornecedor(2L, null, null, null);

		produtos.get(0).setFornecedor(fornecedor2);

		assertExceptionAndMessage(DataIntegrityViolationException.class, Constants.FORNECEDOR_INVALIDO_MESSAGE,
				() -> vendaService.create(venda));
	}

	@Test
	void deveCalcularTotalCompra() throws Exception {

		vendaService.create(venda);

		assertEquals(BigDecimal.valueOf(1500), venda.getTotalCompra());
	}

	@Test
	void deveChamarMetodoCriar() throws Exception {

		vendaService.create(venda);

		verify(vendaRepository).save(venda);
	}

	@Test
	void deveAtualizarVenda() throws Exception {

		Venda vendaRequest = new Venda(null, null, null, null, fornecedor, produtos);

		Venda vendaAtualizada = new Venda(1L, BigDecimal.valueOf(1500), LocalDate.now(), null, fornecedor, produtos);

		when(vendaRepository.findById(1L)).thenReturn(Optional.of(venda));

		when(vendaRepository.save(vendaAtualizada)).thenReturn(vendaAtualizada);

		vendaService.update(1L, vendaRequest);

		verify(vendaRepository).save(vendaAtualizada);

	}

	@Test
	void deveRealizarPesquisa() throws Exception {
		vendaService.pesquisarVendas(filter, pageable);

		verify(vendaRepository).pesquisarVendas(Constants.MIN_DATE, Constants.MAX_DATE, BigDecimal.ZERO,
				Constants.MAX_DECIMAL, pageable);
	}

	@Test
	void deveRetornarMapSuccessTrue() throws Exception {

		doNothing().when(vendaRepository).deleteById(1L);

		assertEquals(Constants.MAP_SUCCESS_TRUE, vendaService.delete(1L));
	}

}
