package com.ednardo.predador.fonte.twitter.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ednardo.predador.dto.TipoFonte;
import com.ednardo.predador.service.FonteServiceImpl;

@Component
public class FonteTwitterServiceImpl {

	public static String ARQUIVO_FONTES_TWITTER = "clubes_twitter.csv";

	@Autowired
	private FonteServiceImpl fonteService;

	public void carregaFontesTwitter(File file) {
		fonteService.carregaFontes(file, TipoFonte.TWITTER);
	}

}
