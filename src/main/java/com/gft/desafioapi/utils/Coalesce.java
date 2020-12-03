package com.gft.desafioapi.utils;

public interface Coalesce<T> {

	T coalesce(T other, Long id);

	default <V> V coalesce(V one, V two) {
		return one != null ? one : two;
	}
}
