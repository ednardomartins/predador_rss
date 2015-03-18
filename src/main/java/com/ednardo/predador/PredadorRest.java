package com.ednardo.predador;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ednardo.predador.adapter.PredadorRSSAdapter;
import com.ednardo.predador.dto.GloboTimesRSS;
import com.ednardo.predador.dto.InformacaoDTO;
import com.ednardo.predador.dto.ResponseNoticias;
import com.ednardo.predador.model.Repository;

@RestController
public class PredadorRest {

	@Resource
	@Qualifier("predadorRSSAdapter")
	private PredadorRSSAdapter adapter;

	@Resource
	@Qualifier("repository")
	private Repository repository;

	@RequestMapping("/feeds")
	public void coletaFeeds() {
		for (String urlTime : GloboTimesRSS.timesRSS) {
			try {
				List<InformacaoDTO> noticias = adapter.getNoticias(urlTime);
				for (InformacaoDTO noticiaDTO : noticias) {
					repository.insereNoticia(noticiaDTO);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@RequestMapping("/noticias")
	public List<InformacaoDTO> getNoticias() {
		List<InformacaoDTO> noticias = repository.getNoticias();
		System.out.println(noticias.size());
		return noticias;
	}

	@RequestMapping(value = "/noticias/{clube}", method = RequestMethod.GET)
	public List<InformacaoDTO> getNoticias(@PathVariable String clube) {
		List<InformacaoDTO> noticias = repository.getNoticias(clube);
		System.out.println(noticias.size());
		ResponseNoticias response = new ResponseNoticias();
		response.setNoticias(noticias);
		return noticias;
	}

}
