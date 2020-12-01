package com.gft.desafioapi.service;

import static com.gft.desafioapi.utils.Coalesce.coalesce;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gft.desafioapi.model.Cliente;
import com.gft.desafioapi.repository.ClienteRepository;

@Service
public class ClienteService implements UserDetailsService {

	@Autowired
	ClienteRepository clienteRepository;

	public Cliente create(Cliente cliente) {

		if (Objects.isNull(cliente.getDataCadastro())) {
			cliente.setDataCadastro(LocalDate.now());
		}

		return clienteRepository.save(cliente);

	}

	public Cliente findClienteById(Long id) {
		return clienteRepository.findById(id).orElseThrow(() -> {
			throw new EmptyResultDataAccessException(1);
		});
	}

	public Cliente update(Long id, Cliente cliente) {
		Cliente clienteExistente = findClienteById(id);

		String nome = coalesce(cliente.getNome(), clienteExistente.getNome());
		String email = coalesce(cliente.getEmail(), clienteExistente.getEmail());
		String senha = coalesce(cliente.getSenha(), clienteExistente.getSenha());
		String documento = coalesce(cliente.getDocumento(), clienteExistente.getDocumento());
		LocalDate dataCadastro = coalesce(cliente.getDataCadastro(), clienteExistente.getDataCadastro());

		Cliente clienteAtualizado = new Cliente(id, nome, email, senha, documento, dataCadastro);

		return clienteRepository.save(clienteAtualizado);
	}

	public ResponseEntity<Map<String, Boolean>> delete(Long id) {
		clienteRepository.deleteById(id);

		return new ResponseEntity<Map<String, Boolean>>(Map.of("success", true), HttpStatus.OK);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Cliente usuario = Optional.ofNullable(clienteRepository.findByEmail(email))
				.orElseThrow(() -> new UsernameNotFoundException("Cliente não encontrado"));

		return new User(usuario.getEmail(), usuario.getSenha(),
				AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN"));
	}
}
