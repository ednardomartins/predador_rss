package com.ednardo.predador.dto;

import org.springframework.data.annotation.Id;

public class FonteInformacaoDTO {

	@Id
	public String idFonte;

	public String nomeClube;

	public TipoFonte tipoFonte;

	public String url;

	public String getIdFonte() {
		return idFonte;
	}

	public void setIdFonte(String idFonte) {
		this.idFonte = idFonte;
	}

	public String getNomeClube() {
		return nomeClube;
	}

	public void setNomeClube(String nomeClube) {
		this.nomeClube = nomeClube;
	}

	public TipoFonte getTipoFonte() {
		return tipoFonte;
	}

	public void setTipoFonte(TipoFonte tipoFonte) {
		this.tipoFonte = tipoFonte;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
