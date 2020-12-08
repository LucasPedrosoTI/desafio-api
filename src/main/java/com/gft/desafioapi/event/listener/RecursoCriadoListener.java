package com.gft.desafioapi.event.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.gft.desafioapi.event.RecursoCriadoEvent;


@Component
public class RecursoCriadoListener implements ApplicationListener<RecursoCriadoEvent> {

	@Override
	public void onApplicationEvent(RecursoCriadoEvent recursoCriadoEvent) {
		HttpServletResponse response = recursoCriadoEvent.getResponse();
		Long id = recursoCriadoEvent.getId();

		adicionarHeaderLocation(id, response);
	}

	private void adicionarHeaderLocation(Long id, HttpServletResponse response) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(id)
				.toUri();

		response.setHeader("Location", uri.toASCIIString());
	}

}
