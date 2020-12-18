package com.gft.desafioapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import com.gft.desafioapi.model.Fornecedor;
import com.gft.desafioapi.model.Produto;
import com.gft.desafioapi.model.Venda;
import com.gft.desafioapi.repository.VendaRepository;
import com.gft.desafioapi.utils.ApiUtils;
import com.gft.desafioapi.utils.Constants;

public class VendaServiceTest {

	@Mock
	private VendaRepository vendaRepository;

	@InjectMocks
	private VendaService vendaService;

	private Venda venda;
	private Fornecedor fornecedor;
	private List<Produto> produtos = new ArrayList<>();

	private Produto produto1;
	private Produto produto2;

	@BeforeEach
	public void setup() throws Exception {

		vendaService = new VendaService();
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

		venda = new Venda(1L, null, LocalDate.now(), null, fornecedor, new ArrayList<>());

		MockitoAnnotations.openMocks(this);
	}

	@Test
	void deveSetarIdNull() throws Exception {

		ApiUtils.setIdNull(venda);

		assertNull(venda.getId());
	}

	@Test
	void deveLancarErroSeNaoHouverFornecedor() throws Exception {
		venda.setFornecedor(null);

		Throwable exceptionThatWasThrown = assertThrows(EmptyResultDataAccessException.class,
				() -> vendaService.create(venda));

		assertThat(exceptionThatWasThrown.getMessage()).isEqualTo(Constants.FORNECEDOR_INEXISTENTE);
	}

	@Test
	void deveLancarErroSeNaoHouverProdutos() throws Exception {
		venda.setProdutos(null);

		Throwable exceptionThatWasThrown = assertThrows(EmptyResultDataAccessException.class,
				() -> vendaService.create(venda));

		assertThat(exceptionThatWasThrown.getMessage()).isEqualTo(Constants.PRODUTO_INEXISTENTE);
	}

	@Test
	void deveLancarErroSeProdutoForDeFornecedorDiferente() throws Exception {
		Fornecedor fornecedor2 = new Fornecedor(2L, null, null, null);

		produtos.get(0).setFornecedor(fornecedor2);

		venda.setProdutos(produtos);

		Throwable exceptionThatWasThrown = assertThrows(DataIntegrityViolationException.class,
				() -> vendaService.create(venda));

		assertThat(exceptionThatWasThrown.getMessage()).isEqualTo(Constants.FORNECEDOR_INVALIDO_MESSAGE);
	}

	@Test
	void deveCalcularTotalCompra() throws Exception {

		venda.setProdutos(produtos);

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

}
