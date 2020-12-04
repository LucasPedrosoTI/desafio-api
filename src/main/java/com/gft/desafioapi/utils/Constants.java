package com.gft.desafioapi.utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Constants {

	public static final LocalDate MIN_DATE = LocalDate.of(1970, 1, 1);
	public static final LocalDate MAX_DATE = LocalDate.of(3000, 12, 31);

	public static final BigDecimal MAX_DECIMAL = BigDecimal.valueOf(9999999);

	public static final String FORNECEDOR_INEXISTENTE = "Fornecedor inexistente ou não informado";
	public static final String FORNECEDOR_INVALIDO_MESSAGE = "Os produtos devem ser do mesmo fornecedor informado";
	public static final String PRODUTO_INEXISTENTE = "Produto inexistente ou não informado";
	public static final String CLIENTE_INEXISTENTE = "Cliente inexistente ou não encontrado";
	public static final String PRODUTO_VALOR_PROMO_MESSAGE = "Se o produto não estiver em promoção, seu valorPromo deve ser igual null, se não, valorPromo é obrigatório";

	public static final ResponseEntity<Map<String, Boolean>> MAP_SUCCESS_TRUE = new ResponseEntity<Map<String, Boolean>>(
			Map.of("success", true), HttpStatus.OK);
}
