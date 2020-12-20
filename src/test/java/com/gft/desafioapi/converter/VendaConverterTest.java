package com.gft.desafioapi.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;

import com.gft.desafioapi.dto.IdDto;
import com.gft.desafioapi.dto.venda.VendaDTORequest;
import com.gft.desafioapi.dto.venda.VendaDTOResponse;
import com.gft.desafioapi.model.Produto;
import com.gft.desafioapi.model.Venda;
import com.gft.desafioapi.repository.ClienteRepository;
import com.gft.desafioapi.repository.FornecedorRepository;
import com.gft.desafioapi.repository.ProdutoRepository;

public class VendaConverterTest {

	@Mock
	private FornecedorRepository fornecedorRepository;

	@Mock
	private ClienteRepository clienteRepository;

	@Mock
	private ProdutoRepository produtoRepository;

	@InjectMocks
	VendaConverter converter;

	private Venda venda;

	private VendaDTORequest request;

	@BeforeEach
	public void setup() throws Exception {

		converter = new VendaConverter();

		venda = Venda.builder()
				.withId(1L)
				.withTotalCompra(BigDecimal.ZERO)
				.withDataCompra(LocalDate.now())
				.withCliente(null)
				.withFornecedor(null)
				.withProdutos(null)
				.build();

		request = VendaDTORequest.builder()
				.withDataCompra(LocalDate.now())
				.withCliente(null)
				.withFornecedor(null)
				.withProdutos(null)
				.build();

		MockitoAnnotations.openMocks(this);
	}

	@Test
	void deveConverterEntityToDto() throws Exception {
		VendaDTOResponse dto = VendaDTOResponse.builder()
				.withId(1L)
				.withTotalCompra(BigDecimal.ZERO)
				.withDataCompra(LocalDate.now())
				.withCliente(null)
				.withFornecedor(null)
				.withProdutos(null)
				.build();

		VendaDTOResponse response = converter.entityToDtoResponse(venda);

		assertEquals(dto, response);
	}

	@Test
	void deveConverterPageDeVendasParaDto() throws Exception {

		Page<VendaDTOResponse> dtos = Page.empty();
		Page<Venda> vendas = Page.empty();

		Page<VendaDTOResponse> response = converter.entityToDtoResponse(vendas);

		assertEquals(dtos, response);

	}

	@Test
	void deveConverterDtoRequestToEntity() throws Exception {

		IdDto idDto = new IdDto(1L);
		Produto produto = Produto.builder().build();

		request.setCliente(idDto);
		request.setFornecedor(idDto);
		request.setProdutos(Arrays.asList(idDto));

		when(fornecedorRepository.getOne(1L)).thenReturn(null);
		when(clienteRepository.getOne(1L)).thenReturn(null);
		when(produtoRepository.getOne(1L)).thenReturn(produto);

		Venda entity = converter.dtoRequestToEntity(request);
		entity.setTotalCompra(BigDecimal.ZERO);
		entity.setId(1L);
		venda.setProdutos(Arrays.asList(produto));

		assertEquals(venda, entity);
	}

	@Test
	void deveConverterParaClienteFornecedorEProdutosNulos() throws Exception {
		Venda entity = converter.dtoRequestToEntity(request);
		entity.setTotalCompra(BigDecimal.ZERO);
		entity.setId(1L);

		assertEquals(venda, entity);
	}

}
