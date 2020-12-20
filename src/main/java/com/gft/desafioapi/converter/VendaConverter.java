package com.gft.desafioapi.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.gft.desafioapi.dto.venda.VendaDTORequest;
import com.gft.desafioapi.dto.venda.VendaDTOResponse;
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

	public VendaDTOResponse entityToDtoResponse(Venda venda) {

		return VendaDTOResponse.builder()
				.withId(venda.getId())
				.withTotalCompra(venda.getTotalCompra())
				.withDataCompra(venda.getDataCompra())
				.withCliente(venda.getCliente())
				.withFornecedor(venda.getFornecedor())
				.withProdutos(venda.getProdutos())
				.build();
	}

	public Page<VendaDTOResponse> entityToDtoResponse(Page<Venda> vendas) {
		return vendas.map(this::entityToDtoResponse);
	}

	public Venda dtoRequestToEntity(VendaDTORequest dto) {

		Fornecedor fornecedor = findFornecedor(dto, fornecedorRepository);
		Cliente cliente = findCliente(dto, clienteRepository);
		List<Produto> produtos = findProdutos(dto, produtoRepository);

		return Venda.builder()
				.withDataCompra(dto.getDataCompra())
				.withCliente(cliente)
				.withFornecedor(fornecedor)
				.withProdutos(produtos)
				.build();
	}

	private List<Produto> findProdutos(VendaDTORequest dto,
			ProdutoRepository produtoRepository) {

		if (dto.getProdutos() != null) {
			return dto.getProdutos()
					.stream()
					.map(produto -> produtoRepository.getOne(produto.getId()))
					.collect(Collectors.toList());
		}

		return null;
	}

	private Cliente findCliente(VendaDTORequest dto, ClienteRepository clienteRepository) {
		if (dto.getCliente() != null && dto.getCliente().getId() != null) {
			return clienteRepository.getOne(dto.getCliente().getId());
		}

		return null;
	}

	private Fornecedor findFornecedor(VendaDTORequest dto, FornecedorRepository fornecedorRepository) {

		if (dto.getFornecedor() != null && dto.getFornecedor().getId() != null) {
			return fornecedorRepository.getOne(dto.getFornecedor().getId());
		}

		return null;
	}

}
