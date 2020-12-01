package com.gft.desafioapi.utils;

public class Coalesce {

	public static <T> T coalesce(T one, T two) {
		return one != null ? one : two;
	}
}
