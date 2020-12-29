package com.gft.desafioapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.gft.desafioapi.model.Cliente;
import com.gft.desafioapi.repository.ClienteRepository;
import com.gft.desafioapi.repository.filter.ClienteFilter;
import com.gft.desafioapi.utils.Constants;

class ClientServiceTest {

	@Mock
	private ClienteRepository clienteRepository;

	@Mock
	private BCryptPasswordEncoder encoder;

	@Mock
	private Pageable pageable;

	@InjectMocks
	private ClienteService clienteService;

	private Cliente cliente;
	private Cliente clienteEmBanco;
	@BeforeEach
	public void setup() throws Exception {
		cliente = Cliente.builder()
				.withId(1L)
				.withNome("Nome")
				.withEmail("email@email.com")
				.withSenha("123456")
				.withDocumento("123456789")
				.build();

		clienteEmBanco = cliente;
		clienteEmBanco.setSenha("$2a$10$Km3aDWfDD.yfONVpgm5UIOpq8N2NLLeTZtfz7gGOp3WPW8fMWYH3G");
		clienteEmBanco.setDataCadastro(LocalDate.now());

		clienteService = new ClienteService();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void deveLancarErroAoExcluirClienteComIdInexistente() throws Exception {
		Long id = 10L;

		doThrow(EmptyResultDataAccessException.class).when(clienteRepository).deleteById(id);

		assertThrows(EmptyResultDataAccessException.class, () -> clienteService.delete(id));
	}

	@Test
	void deveRetornarMapSuccessTrue() throws Exception {

		doNothing().when(clienteRepository).deleteById(1L);

		assertEquals(Constants.MAP_SUCCESS_TRUE, clienteService.delete(1L));

	}

	@Test
	void deveSetarDataAtualQuandoNull() throws Exception {
		clienteService.create(cliente);

		assertEquals(LocalDate.now(), cliente.getDataCadastro());
	}

	@Test
	void naoDeveSetarADataQuandoPresenteNaRequisicao() throws Exception {
		LocalDate date = LocalDate.of(2020, 12, 1);
		cliente.setDataCadastro(date);

		clienteService.create(cliente);

		assertEquals(date, cliente.getDataCadastro());
	}

	@Test
	void deveCriarUmCliente() throws Exception {

		mockEncodarSenha();

		clienteService.create(cliente);

		clienteEmBanco.setId(null);

		verify(clienteRepository).save(clienteEmBanco);
	}

	@Test
	void deveAtualizarClienteNoBancoEncodandoSenhaPassada() throws Exception {
		Cliente atualizacao = Cliente.builder().withNome("Nome Completo").withSenha("123456").build();

		mockEncodarSenha();

		mockFindById();

		clienteService.update(1L, atualizacao);

		clienteEmBanco.setNome("Nome Completo");

		verify(clienteRepository).save(clienteEmBanco);
	}

	@Test
	void naoDeveEncodarCasoSenhaSejaPassadaNulaNoUpdate() throws Exception {
		Cliente atualizacao = Cliente.builder()
				.withNome("Nome Completo")
				.build();

		mockFindById();

		clienteService.update(1L, atualizacao);

		clienteEmBanco.setNome("Nome Completo");

		verify(clienteRepository).save(clienteEmBanco);
	}

	@Test
	void deveNormalizarFilterDaPesquisa() throws Exception {

		ClienteFilter filter = new ClienteFilter();

		clienteService.pesquisarClientes(filter, pageable);

		verify(clienteRepository).pesquisarClientes("", "", "", Constants.MIN_DATE, Constants.MAX_DATE, pageable);
	}

	private void mockFindById() {
		when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteEmBanco));
	}

	private void mockEncodarSenha() {
		when(encoder.encode("123456")).thenReturn("$2a$10$Km3aDWfDD.yfONVpgm5UIOpq8N2NLLeTZtfz7gGOp3WPW8fMWYH3G");
	}


}
