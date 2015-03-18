package com.ednardo.predador.dto;

import java.io.Serializable;
import java.util.List;

public class ResponseNoticias implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<InformacaoDTO> noticias;

	public List<InformacaoDTO> getNoticias() {
		return noticias;
	}

	public void setNoticias(List<InformacaoDTO> noticias) {
		this.noticias = noticias;
	}

}
