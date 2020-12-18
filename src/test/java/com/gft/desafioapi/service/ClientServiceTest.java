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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.gft.desafioapi.model.Cliente;
import com.gft.desafioapi.repository.ClienteRepository;
import com.gft.desafioapi.utils.Constants;

public class ClientServiceTest {

	@Mock
	private ClienteRepository clienteRepository;

	@Mock
	private BCryptPasswordEncoder encoder;

	@InjectMocks
	private ClienteService clienteService;

	private Cliente cliente;
	private Cliente clienteEmBanco;
	@BeforeEach
	public void setup() throws Exception {
		cliente = new Cliente(1L, "Nome", "email@email.com", "123456", "123456789", null);

		clienteEmBanco = new Cliente(1L, "Nome", "email@email.com",
				"$2a$10$Km3aDWfDD.yfONVpgm5UIOpq8N2NLLeTZtfz7gGOp3WPW8fMWYH3G", "123456789", LocalDate.now());

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
		Cliente atualizacao = new Cliente(null, "Nome Completo", null, "123456", null, null);

		mockEncodarSenha();

		mockFindById();

		clienteService.update(1L, atualizacao);

		clienteEmBanco.setNome("Nome Completo");

		verify(clienteRepository).save(clienteEmBanco);
	}

	@Test
	void naoDeveEncodarCasoSenhaSejaPassadaNulaNoUpdate() throws Exception {
		Cliente atualizacao = new Cliente(null, "Nome Completo", null, null, null, null);

		mockFindById();

		clienteService.update(1L, atualizacao);

		clienteEmBanco.setNome("Nome Completo");

		verify(clienteRepository).save(clienteEmBanco);
	}

	private void mockFindById() {
		when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteEmBanco));
	}

	private void mockEncodarSenha() {
		when(encoder.encode("123456")).thenReturn("$2a$10$Km3aDWfDD.yfONVpgm5UIOpq8N2NLLeTZtfz7gGOp3WPW8fMWYH3G");
	}

}
