package com.ednardo.predador;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ednardo.predador.adapter.PredadorRSSAdapter;
import com.ednardo.predador.dto.GloboTimesRSS;
import com.ednardo.predador.dto.InformacaoDTO;
import com.ednardo.predador.model.Repository;

@Component
@Configuration
@EnableScheduling
public class PredadorGerenciadorNoticias {

	@Resource
	@Qualifier("predadorRSSAdapter")
	private PredadorRSSAdapter adapter;

	@Resource
	@Qualifier("repository")
	private Repository repository;

	private static Logger log = Logger.getLogger(PredadorGerenciadorNoticias.class);

	@Scheduled(fixedRate = 180000)
	public void coletaFeeds() {
		for (String urlTime : GloboTimesRSS.timesRSS) {
			try {
				List<InformacaoDTO> noticias = adapter.getNoticias(urlTime);
				for (InformacaoDTO noticiaDTO : noticias) {
					repository.insereNoticia(noticiaDTO);
				}
			} catch (Exception e) {
				log.error("Erro ao processar a URL: " + urlTime, e);
			}
		}
		log.debug("Fim da coleta.");
	}

}
