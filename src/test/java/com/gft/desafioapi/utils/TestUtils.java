package com.gft.desafioapi.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.function.Executable;

public class TestUtils {

	public static <T extends RuntimeException> void assertExceptionAndMessage(Class<T> exceptionClass,
			String expectedMessage, Executable executableMethod) {
		Throwable exception = assertThrows(exceptionClass, executableMethod);
		assertThat(exception.getMessage()).isEqualTo(expectedMessage);
	}

}
