package com.gft.desafioapi.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

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
		return vendas.stream().map(this::entityToDto).collect(Collectors.toList());
	}

	public Page<VendaDTO> entityToDto(Page<Venda> vendas) {
		return vendas.map(this::entityToDto);
	}

	public Venda dtoToEntity(VendaDTO dto) {
		return new Venda(dto.getId(), dto.getTotalCompra(), dto.getDataCompra(), dto.getCliente(), dto.getFornecedor(),
				dto.getProdutos());
	}

	public Venda dtoToEntity(VendaDTORequest dto) {

		Venda venda = new Venda();

		Fornecedor fornecedor = null;
		Cliente cliente = null;
		List<Produto> produtos = null;

		if (dto.getFornecedor() != null && dto.getFornecedor().getId() != null) {
			fornecedor = fornecedorRepository.getOne(dto.getFornecedor().getId());
		}

		if (dto.getCliente() != null && dto.getCliente().getId() != null) {
			cliente = clienteRepository.getOne(dto.getCliente().getId());
		}

		if (dto.getProdutos() != null) {
			produtos = dto.getProdutos().stream().map(produto -> produtoRepository.getOne(produto.getId()))
					.collect(Collectors.toList());
		}

		venda.setDataCompra(dto.getDataCompra());
		venda.setCliente(cliente);
		venda.setFornecedor(fornecedor);
		venda.setProdutos(produtos);

		return venda;
	}

	public List<Venda> dtoToEntity(List<VendaDTO> dtos) {
		return dtos.stream().map(this::dtoToEntity).collect(Collectors.toList());
	}
}
