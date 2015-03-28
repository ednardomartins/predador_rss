package com.ednardo.predador.fonte.rss;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.ednardo.predador.fonte.rss.adapter.PredadorRSSAdapter;
import com.ednardo.predador.model.RepositoryFonte;
import com.ednardo.predador.model.RepositoryInformacao;

@Component
@Configuration
@EnableScheduling
public class PredadorGerenciadorNoticiasImpl {

	@Resource
	@Qualifier("predadorRSSAdapter")
	private PredadorRSSAdapter adapter;

	@Resource
	@Qualifier("repositoryInformacao")
	private RepositoryInformacao repository;

	@Resource
	@Qualifier("repositoryFonte")
	private RepositoryFonte repositoryFonte;

	private static Logger log = Logger.getLogger(PredadorGerenciadorNoticiasImpl.class);

	// @Scheduled(fixedRate = 360000)
	// public void coletaFeeds() {
	// List<FonteInformacaoDTO> fontesInformacoes =
	// repositoryFonte.getFontesInformacoes(TipoFonte.FEED_RSS);
	// for (FonteInformacaoDTO fonte : fontesInformacoes) {
	// try {
	// List<InformacaoDTO> noticias = adapter.getNoticias(fonte.getUrl());
	// for (InformacaoDTO noticiaDTO : noticias) {
	// repository.insereInformacao(noticiaDTO);
	// }
	// } catch (Exception e) {
	// log.error("Erro ao processar a URL: " + fonte.getUrl(), e);
	// }
	// }
	// log.debug("Fim da coleta.");
	// }

}
