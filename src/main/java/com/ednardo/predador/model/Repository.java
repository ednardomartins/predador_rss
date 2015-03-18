package com.ednardo.predador.model;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.ednardo.predador.dto.InformacaoDTO;

@Component
public class Repository {

	public static final String CLUBES = "times";
	public static final String INFORMACOES_CLUBES = "timesNoticias";

	@Autowired
	private MongoOperations mongoOps;

	@Bean
	public Repository getRepository() {
		return new Repository();
	}

	private static Logger log = Logger.getLogger(Repository.class);

	public void insereInformacao(List<InformacaoDTO> noticias) {
		try {
			mongoOps.insert(noticias, INFORMACOES_CLUBES);
		} catch (Exception e) {
			log.error("Erro ao inserir a lista de noticias", e);
		}
	}

	public void insereNoticia(InformacaoDTO noticia) {
		try {
			mongoOps.insert(noticia, INFORMACOES_CLUBES);
		} catch (DuplicateKeyException due) {
			log.warn("noticia com a chave: " + noticia.getId() + "duplicada.");
		} catch (Exception e) {
			log.error("Erro ao inserir a lista de noticias", e);
		}
	}

	public void update(InformacaoDTO noticia) {
		try {
			mongoOps.save(noticia, INFORMACOES_CLUBES);
		} catch (Exception e) {
			log.error("Erro ao inserir a lista de noticias", e);
		}
	}

	public InformacaoDTO getNoticia(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		return mongoOps.findOne(query, InformacaoDTO.class, INFORMACOES_CLUBES);
	}

	public List<InformacaoDTO> getNoticias() {
		return mongoOps.findAll(InformacaoDTO.class, INFORMACOES_CLUBES);
	}

	public List<InformacaoDTO> getNoticias(String clube) {
		Query query = new Query(Criteria.where("clube").is(clube));
		return mongoOps.find(query, InformacaoDTO.class, INFORMACOES_CLUBES);
	}

}
