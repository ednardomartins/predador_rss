package com.ednardo.predador;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ednardo.predador.dto.FonteInformacaoDTO;
import com.ednardo.predador.dto.InformacaoDTO;
import com.ednardo.predador.dto.TipoFonte;
import com.ednardo.predador.fonte.rss.adapter.PredadorRSSAdapter;
import com.ednardo.predador.fonte.twitter.service.FonteTwitterServiceImpl;
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

	@Resource
	@Qualifier("fonteTwitterServiceImpl")
	private FonteTwitterServiceImpl fontes;

	@Autowired
	ServletContext servletContext;

	@PostConstruct
	public void Init() {
		String path = "";
		try {
			path = Paths
					.get(servletContext.getClassLoader().getResource(FonteTwitterServiceImpl.ARQUIVO_FONTES_TWITTER)
							.toURI()).toString();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fontes.carregaFontesTwitter(new File(path));
	}

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
