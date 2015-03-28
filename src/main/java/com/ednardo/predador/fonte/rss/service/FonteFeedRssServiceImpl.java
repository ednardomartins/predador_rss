package com.ednardo.predador.fonte.rss.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ednardo.predador.dto.TipoFonte;
import com.ednardo.predador.service.FonteServiceImpl;

@Component
public class FonteFeedRssServiceImpl {

	private static String ARQUIVO_FONTES_RSS = "/feedrss_globoesporte.csv";

	@Autowired
	private FonteServiceImpl fonteService;

	@PostConstruct
	public void carregaFontesRss() {
		fonteService.carregaFontes(ARQUIVO_FONTES_RSS, TipoFonte.FEED_RSS);
	}

}
