package com.ednardo.predador.model;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.ednardo.predador.dto.InformacaoDTO;

@Component
public class RepositoryInformacao {

	public static final String CLUBES = "times";
	public static final String INFORMACOES_CLUBES = "timesNoticias";

	@Autowired
	private MongoOperations mongoOps;

	@Bean
	public RepositoryInformacao getRepository() {
		return new RepositoryInformacao();
	}

	private static Logger log = Logger.getLogger(RepositoryInformacao.class);

	public void insereInformacao(List<InformacaoDTO> informacoes) {
		try {
			mongoOps.insert(informacoes, INFORMACOES_CLUBES);
		} catch (Exception e) {
			log.error("Erro ao inserir a lista de noticias", e);
		}
	}

	public void insereInformacao(InformacaoDTO informacao) {
		try {
			mongoOps.insert(informacao, INFORMACOES_CLUBES);
		} catch (DuplicateKeyException due) {
			log.warn("noticia com a chave: " + informacao.getId() + "duplicada.");
		} catch (Exception e) {
			log.error("Erro ao inserir a lista de noticias", e);
		}
	}

	public void update(InformacaoDTO informacao) {
		try {
			mongoOps.save(informacao, INFORMACOES_CLUBES);
		} catch (Exception e) {
			log.error("Erro ao inserir a lista de noticias", e);
		}
	}

	public InformacaoDTO getInformacao(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		return mongoOps.findOne(query, InformacaoDTO.class, INFORMACOES_CLUBES);
	}

	public List<InformacaoDTO> getInformacoes() {
		Query query = new Query();
		query.with(new Sort(Sort.Direction.DESC, "data"));
		return mongoOps.find(query, InformacaoDTO.class, INFORMACOES_CLUBES);
	}

	public List<InformacaoDTO> getInformacoes(String clube) {
		Query query = new Query(Criteria.where("clube").is(clube));
		return mongoOps.find(query, InformacaoDTO.class, INFORMACOES_CLUBES);
	}

}
