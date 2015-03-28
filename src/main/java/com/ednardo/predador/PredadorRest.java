package com.ednardo.predador;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ednardo.predador.dto.FonteInformacaoDTO;
import com.ednardo.predador.dto.InformacaoDTO;
import com.ednardo.predador.dto.TipoFonte;
import com.ednardo.predador.fonte.rss.adapter.PredadorRSSAdapter;
import com.ednardo.predador.model.RepositoryFonte;
import com.ednardo.predador.model.RepositoryInformacao;

@RestController
public class PredadorRest {

	@Resource
	@Qualifier("predadorRSSAdapter")
	private PredadorRSSAdapter adapter;

	@Resource
	@Qualifier("repositoryInformacao")
	private RepositoryInformacao repository;

	@Resource
	@Qualifier("repositoryFonte")
	private RepositoryFonte repositoryFonte;

	@RequestMapping("/informacoesClube")
	public List<InformacaoDTO> getInformacoes() {
		List<InformacaoDTO> noticias = repository.getInformacoes();
		System.out.println(noticias.size());
		return noticias;
	}

	@RequestMapping(value = "/informacoesClube/{clube}", method = RequestMethod.GET)
	public List<InformacaoDTO> getInformacoes(@PathVariable String clube) {
		List<InformacaoDTO> informacoes = repository.getInformacoes(clube);
		System.out.println(informacoes.size());
		return informacoes;
	}

	@RequestMapping("/fontesRss")
	public List<FonteInformacaoDTO> getFontesRss() {
		List<FonteInformacaoDTO> fontes = repositoryFonte.getFontesInformacoes(TipoFonte.FEED_RSS);
		System.out.println(fontes.size());
		return fontes;
	}

	@RequestMapping("/fontesTwitter")
	public List<FonteInformacaoDTO> getFontesTwitter() {
		List<FonteInformacaoDTO> fontes = repositoryFonte.getFontesInformacoes(TipoFonte.TWITTER);
		System.out.println(fontes.size());
		return fontes;
	}
}
