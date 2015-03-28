package com.ednardo.predador.fonte.rss.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.xml.sax.InputSource;

import com.ednardo.predador.dto.InformacaoDTO;
import com.ednardo.predador.model.mongo.security.HashGenerate;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;

@Component
public class PredadorRSSAdapter {

	private static Logger log = Logger.getLogger(PredadorRSSAdapter.class);

	public PredadorRSSAdapter getPredadorRSSAdapter() {
		return new PredadorRSSAdapter();
	}

	public List<InformacaoDTO> getNoticias(String url) throws MalformedURLException, IllegalArgumentException,
			IOException, FeedException {
		List<InformacaoDTO> noticias = new ArrayList<InformacaoDTO>();
		SyndFeed feed = getSyndFeedForUrl(url);
		@SuppressWarnings("unchecked")
		List<SyndEntryImpl> entries = feed.getEntries();
		for (SyndEntryImpl nFeed : entries) {
			noticias.add(convertFeedParaNoticiaDTO(nFeed, feed.getTitle()));
		}
		return noticias;
	}

	private InformacaoDTO convertFeedParaNoticiaDTO(SyndEntryImpl nFeed, String clube) {
		InformacaoDTO dto = new InformacaoDTO();
		dto.setClube(clube);
		dto.setLink(nFeed.getLink());
		dto.setTitulo(nFeed.getTitle());
		dto.setTexto(nFeed.getDescription().getValue());
		dto.setData(nFeed.getPublishedDate());
		try {
			dto.setId(HashGenerate.hashString(nFeed.getLink()));
		} catch (Exception e) {
			log.error("erro a gerar o hash id", e);
		}
		return dto;
	}

	public SyndFeed getSyndFeedForUrl(String url) throws MalformedURLException, IOException, IllegalArgumentException,
			FeedException {
		SyndFeed feed = null;
		InputStream is = null;
		try {

			URLConnection openConnection = new URL(url).openConnection();
			is = new URL(url).openConnection().getInputStream();
			if ("gzip".equals(openConnection.getContentEncoding())) {
				is = new GZIPInputStream(is);
			}
			InputSource source = new InputSource(is);
			SyndFeedInput input = new SyndFeedInput();
			feed = input.build(source);

		} catch (Exception e) {
			log.error("Exception occured when building the feed object out of the url", e);
		} finally {
			if (is != null)
				is.close();
		}

		return feed;
	}

}
