package com.ednardo.predador.fonte.twitter.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.UserMentionEntity;
import twitter4j.conf.ConfigurationBuilder;

import com.ednardo.predador.dto.FonteInformacaoDTO;
import com.ednardo.predador.dto.InformacaoDTO;
import com.ednardo.predador.dto.TipoFonte;
import com.ednardo.predador.model.RepositoryFonte;
import com.ednardo.predador.model.RepositoryInformacao;

@Component
public class TwitterServiceStreamImpl {

	@Resource
	@Qualifier("repositoryInformacao")
	private RepositoryInformacao repository;

	@Resource
	@Qualifier("repositoryFonte")
	private RepositoryFonte repositoryFonte;

	private static Map<String, String> clubes = new HashMap<String, String>();

	public static TwitterStream getStream() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey(ConstantSecurity.CONSUMER_KEY);
		cb.setOAuthConsumerSecret(ConstantSecurity.CONSUMER_SECRET);
		cb.setOAuthAccessToken(ConstantSecurity.ACESS_TOKEN);
		cb.setOAuthAccessTokenSecret(ConstantSecurity.ACESS_TOKEN_SECRET);
		return new TwitterStreamFactory(cb.build()).getInstance();
	}

	@PostConstruct
	public void init() {
		List<FonteInformacaoDTO> fontesInformacoes = repositoryFonte.getFontesInformacoes(TipoFonte.TWITTER);
		for (FonteInformacaoDTO fonteInformacaoDTO : fontesInformacoes) {
			clubes.put(fonteInformacaoDTO.getUrl(), fonteInformacaoDTO.getNomeClube());
		}
		readTwitterFeed();
	}

	public void readTwitterFeed() {

		TwitterStream stream = getStream();

		StatusListener listener = new StatusListener() {

			@Override
			public void onException(Exception e) {
				System.out.println("Exception occured:" + e.getMessage());
				e.printStackTrace();
			}

			@Override
			public void onTrackLimitationNotice(int n) {
				System.out.println("Track limitation notice for " + n);
			}

			@Override
			public void onStatus(Status status) {
				System.out.println("Got twit:" + status.getText());
				InformacaoDTO dto = new InformacaoDTO();
				dto.setTexto(status.getText());
				dto.setData(status.getCreatedAt());
				dto.setTitulo("tweet");
				List<String> clubesMencionados = getClubes(status);
				for (String clube : clubesMencionados) {
					dto.setClube(clube);
					repository.insereInformacao(dto);
				}

			}

			private List<String> getClubes(Status status) {
				List<String> clubesMencionados = new ArrayList<String>();
				String clube = clubes.get(status.getUser().getScreenName());

				if (clube != null && !clube.isEmpty()) {
					clubesMencionados.add(clube);
					return clubesMencionados;
				}

				UserMentionEntity[] userMentionEntities = status.getUserMentionEntities();
				for (UserMentionEntity userMentionEntity : userMentionEntities) {
					String clubeMencionado = clubes.get(userMentionEntity.getScreenName());
					if (clubeMencionado != null && !clubeMencionado.isEmpty()) {
						clubesMencionados.add(clubeMencionado);
					}
				}
				return clubesMencionados;
			}

			@Override
			public void onStallWarning(StallWarning arg0) {
				System.out.println("Stall warning");
			}

			@Override
			public void onScrubGeo(long arg0, long arg1) {
				System.out.println("Scrub geo with:" + arg0 + ":" + arg1);
			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice arg0) {
				System.out.println("Status deletion notice");
			}
		};

		FilterQuery qry = new FilterQuery();
		String[] keywords = clubes.keySet().toArray(new String[clubes.size()]);

		qry.track(keywords);

		stream.addListener(listener);
		stream.filter(qry);
	}

	public static void main(String[] args) {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey(ConstantSecurity.CONSUMER_KEY);
		cb.setOAuthConsumerSecret(ConstantSecurity.CONSUMER_SECRET);
		cb.setOAuthAccessToken(ConstantSecurity.ACESS_TOKEN);
		cb.setOAuthAccessTokenSecret(ConstantSecurity.ACESS_TOKEN_SECRET);

		new TwitterStreamFactory(cb.build()).getInstance();

	}
}
