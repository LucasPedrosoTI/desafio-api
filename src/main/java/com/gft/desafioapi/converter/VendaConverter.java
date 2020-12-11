package com.gft.desafioapi.converter;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.gft.desafioapi.dto.AbstractDtoId;
import com.gft.desafioapi.dto.venda.VendaDTO;
import com.gft.desafioapi.dto.venda.VendaDTORequest;
import com.gft.desafioapi.model.Cliente;
import com.gft.desafioapi.model.Fornecedor;
import com.gft.desafioapi.model.Produto;
import com.gft.desafioapi.model.Venda;
import com.gft.desafioapi.repository.ClienteRepository;
import com.gft.desafioapi.repository.FornecedorRepository;
import com.gft.desafioapi.repository.ProdutoRepository;

@Component
public class VendaConverter {

	@Autowired
	ProdutoRepository produtoRepository;

	@Autowired
	FornecedorRepository fornecedorRepository;

	@Autowired
	ClienteRepository clienteRepository;

	public VendaDTO entityToDto(Venda venda) {

		return new VendaDTO(venda.getId(), venda.getTotalCompra(), venda.getDataCompra(), venda.getCliente(),
				venda.getFornecedor(), venda.getProdutos());
	}

	public List<VendaDTO> entityToDto(List<Venda> vendas) {
		return vendas.stream()
				.map(this::entityToDto)
				.collect(Collectors.toList());
	}

	public Page<VendaDTO> entityToDto(Page<Venda> vendas) {
		return vendas.map(this::entityToDto);
	}

	public Venda dtoToEntity(VendaDTO dto) {
		return new Venda(dto.getId(), dto.getTotalCompra(), dto.getDataCompra(), dto.getCliente(),
				dto.getFornecedor(), dto.getProdutos());
	}

	public Venda dtoToEntity(VendaDTORequest dto) {

		Venda venda = new Venda();

		AbstractDtoId fornecedorDto = Optional.ofNullable(dto.getFornecedor())
				.orElse(null);
		AbstractDtoId clienteDto = Optional.ofNullable(dto.getCliente())
				.orElse(null);

		Long fornecedorId = null;
		Long clienteId = null;

		Fornecedor fornecedor = null;
		Cliente cliente = null;
		List<Produto> produtos = null;

		if (Objects.nonNull(fornecedorDto)) {
			fornecedorId = fornecedorDto.getId();
		}

		if (Objects.nonNull(clienteDto)) {
			clienteId = clienteDto.getId();
		}

		if (Objects.nonNull(fornecedorId)) {
			fornecedor = fornecedorRepository.findById(fornecedorId)
					.orElse(null);
		}

		if (Objects.nonNull(clienteId)) {
			cliente = clienteRepository.findById(clienteId)
					.orElse(null);
		}

		if (dto.getProdutos() != null) {
			produtos = dto.getProdutos()
					.stream()
					.map(produto -> produtoRepository.getOne(produto.getId()))
					.collect(Collectors.toList());
		}

		venda.setDataCompra(dto.getDataCompra());
		venda.setCliente(cliente);
		venda.setFornecedor(fornecedor);
		venda.setProdutos(produtos);

		return venda;
	}

	public List<Venda> dtoToEntity(List<VendaDTO> dtos) {
		return dtos.stream()
				.map(this::dtoToEntity)
				.collect(Collectors.toList());
	}
}
