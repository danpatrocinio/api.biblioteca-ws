package org.bairro.biblioteca.utils;

public class MensagemResposta {
	
	/**
	 * Classe utilitária para retornar um objeto JSON padrão como resposta
	 */
	
	private String mensagem;

	public MensagemResposta() {
	}

	public MensagemResposta(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}
