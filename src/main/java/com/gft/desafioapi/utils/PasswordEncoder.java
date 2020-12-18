package com.gft.desafioapi.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {

	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		for (int i = 0; i < 1; i++) {
			System.out.println(encoder.encode("123456"));
		}

	}

}
