package com.ednardo.predador.dto;

public enum TipoFonte {

	TWITTER("twitter"), FEED_RSS("feedRss");

	private final String tipo;

	TipoFonte(String tipo) {
		this.tipo = tipo;
	}

	public String valor() {
		return tipo;
	}

}
