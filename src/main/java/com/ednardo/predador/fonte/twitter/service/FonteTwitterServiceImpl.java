package com.ednardo.predador.fonte.twitter.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ednardo.predador.dto.TipoFonte;
import com.ednardo.predador.service.FonteServiceImpl;

@Component
public class FonteTwitterServiceImpl {

	private static String ARQUIVO_FONTES_TWITTER = "/clubes_twitter.csv";

	@Autowired
	private FonteServiceImpl fonteService;

	@PostConstruct
	public void carregaFontesTwitter() {
		fonteService.carregaFontes(ARQUIVO_FONTES_TWITTER, TipoFonte.TWITTER);
	}

}
