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
		fornecedor = Fornecedor.builder()
				.withId(1L)
				.withNome("Nome")
				.withCnpj("12345678912345")
				.withProdutos(new ArrayList<>())
				.build();

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

		venda = Venda.builder()
				.withId(1L)
				.withCliente(null)
				.withDataCompra(LocalDate.now())
				.withTotalCompra(BigDecimal.ZERO)
				.withFornecedor(fornecedor)
				.withProdutos(produtos)
				.build();

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
		Fornecedor fornecedor2 = Fornecedor.builder().withId(2L).build();

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

		LocalDate data = LocalDate.of(2020, 12, 1);

		Venda atualizacao = Venda.builder().withDataCompra(data).withProdutos(null).build();

		when(vendaRepository.findById(1L)).thenReturn(Optional.of(venda));

		vendaService.update(1L, atualizacao);

		venda.setDataCompra(data);
		venda.setTotalCompra(BigDecimal.valueOf(1500));

		verify(vendaRepository).save(venda);

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
