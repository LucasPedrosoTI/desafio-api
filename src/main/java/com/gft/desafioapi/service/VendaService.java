package com.gft.desafioapi.service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gft.desafioapi.model.Venda;
import com.gft.desafioapi.repository.VendaRepository;
import com.gft.desafioapi.repository.filter.VendaFilter;
import com.gft.desafioapi.utils.ApiUtils;
import com.gft.desafioapi.utils.Constants;

@Service
public class VendaService {

	@Autowired
	VendaRepository vendaRepository;

	public Page<Venda> pesquisarVendas(VendaFilter filter, Pageable pageable) {

		//		Map<String, Map<String, Object>> verifiedFilter = filter.removeNullValues(filter, new VendaFilter());

		//		return vendaRepository.pesquisarVendas(
		//				LocalDate.parse(filter.getValueFrom("dataCompraDe", verifiedFilter)),
		//				LocalDate.parse(filter.getValueFrom("dataCompraAte",
		//						verifiedFilter)),
		//				new BigDecimal(filter.getValueFrom("totalCompraDe", verifiedFilter)),
		//				new BigDecimal(filter.getValueFrom("totalCompraAte",
		//						verifiedFilter)),
		//				pageable);
		return vendaRepository.pesquisarVendas(
				filter.getDataCompraDe(),
				filter.getDataCompraAte(),
				filter.getTotalCompraDe(),
				filter.getTotalCompraAte(),
				pageable);
	}

	public Venda findVendaById(Long id) {
		return vendaRepository.findById(id).orElseThrow(() -> {
			throw new EmptyResultDataAccessException(1);
		});
	}

	public Venda create(Venda venda) {
		ApiUtils.setIdNull(venda);

		checkFornecedor(venda);

		checkProdutos(venda);

		validarSeProdutosPertencemAoFornecedor(venda);

		venda.setTotalCompra(calcularTotalCompra(venda));

		return vendaRepository.save(venda);
	}

	public Venda update(Long id, Venda venda) {
		Venda vendaAtualizada = venda.coalesce(findVendaById(id), id);

		validarSeProdutosPertencemAoFornecedor(vendaAtualizada);

		vendaAtualizada.setTotalCompra(calcularTotalCompra(vendaAtualizada));

		return vendaRepository.save(vendaAtualizada);
	}

	public ResponseEntity<Map<String, Boolean>> delete(Long id) {
		vendaRepository.deleteById(id);
		return Constants.MAP_SUCCESS_TRUE;
	}

	private void validarSeProdutosPertencemAoFornecedor(Venda venda) {
		venda.getProdutos().forEach(produto -> {
			if (!produto.getFornecedor().getId().equals(venda.getFornecedor().getId())) {
				throw new DataIntegrityViolationException(Constants.FORNECEDOR_INVALIDO_MESSAGE);
			}
		});
	}

	private BigDecimal calcularTotalCompra(Venda venda) {
		return venda.getProdutos()
				.stream()
				.map(p -> p.isPromocao() ? p.getValorPromo() : p.getValor())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private void checkProdutos(Venda venda) {
		if (Objects.isNull(venda.getProdutos())) {
			throw new EmptyResultDataAccessException(Constants.PRODUTO_INEXISTENTE, 1);
		}
	}

	private void checkFornecedor(Venda venda) {
		if (Objects.isNull(venda.getFornecedor())) {
			throw new EmptyResultDataAccessException(Constants.FORNECEDOR_INEXISTENTE, 1);
		}
	}

}
