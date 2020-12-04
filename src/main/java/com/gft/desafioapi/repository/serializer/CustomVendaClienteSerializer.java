package com.gft.desafioapi.repository.serializer;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.gft.desafioapi.model.Cliente;

public class CustomVendaClienteSerializer extends StdSerializer<Cliente> {

	private static final long serialVersionUID = 1057276000702591921L;

	public CustomVendaClienteSerializer() {
		this(null);
	}

	public CustomVendaClienteSerializer(Class<Cliente> t) {
		super(t);
	}

	@Override
	public void serialize(Cliente cliente, JsonGenerator generator, SerializerProvider provider) throws IOException {
		generator.writeObject(Map.of("id", cliente.getId()));
	}

}
