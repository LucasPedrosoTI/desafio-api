package com.gft.desafioapi.exceptionHandler;

public class Erro {
	private String mensagemUsuario;
	private String mensagemDev;

	public Erro(String mensagemUsuario, String mensagemDev) {
		this.mensagemUsuario = mensagemUsuario;
		this.mensagemDev = mensagemDev;
	}

	public String getMensagemUsuario() {
		return mensagemUsuario;
	}

	public void setMensagemUsuario(String mensagemUsuario) {
		this.mensagemUsuario = mensagemUsuario;
	}

	public String getMensagemDev() {
		return mensagemDev;
	}

	public void setMensagemDev(String mensagemDev) {
		this.mensagemDev = mensagemDev;
	}
}
