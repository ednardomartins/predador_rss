package com.ednardo.predador.model;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.ednardo.predador.dto.FonteInformacaoDTO;
import com.ednardo.predador.dto.TipoFonte;

@Component
public class RepositoryFonte {

	public static final String FONTE_INFORMACOES = "fonteInformacao";

	@Resource
	@Qualifier("connection")
	private MongoOperations mongoOps;

	@Bean
	public RepositoryFonte getRepository() {
		return new RepositoryFonte();
	}

	private static Logger log = Logger.getLogger(RepositoryInformacao.class);

	public void insereFonteInformacao(List<FonteInformacaoDTO> fontes) {
		try {
			mongoOps.insert(fontes, FONTE_INFORMACOES);
		} catch (Exception e) {
			log.error("Erro ao inserir fontes", e);
		}
	}

	public void insereFonteInformacao(FonteInformacaoDTO fonte) {
		try {
			mongoOps.insert(fonte, FONTE_INFORMACOES);
		} catch (DuplicateKeyException due) {
			log.warn("fonte com a chave: " + fonte.getIdFonte() + "duplicada.");
		} catch (Exception e) {
			log.error("Erro ao inserir fontes", e);
		}
	}

	public void update(FonteInformacaoDTO fonte) {
		try {
			mongoOps.save(fonte, FONTE_INFORMACOES);
		} catch (Exception e) {
			log.error("Erro ao inserir a lista de noticias", e);
		}
	}

	public FonteInformacaoDTO getFonteInformacao(String id) {
		Query query = new Query(Criteria.where("idFonte").is(id));
		return mongoOps.findOne(query, FonteInformacaoDTO.class, FONTE_INFORMACOES);
	}

	public List<FonteInformacaoDTO> getFontesInformacoes() {
		return mongoOps.findAll(FonteInformacaoDTO.class, FONTE_INFORMACOES);
	}

	public List<FonteInformacaoDTO> getFontesInformacoes(String clube) {
		Query query = new Query(Criteria.where("nomeClube").is(clube));
		return mongoOps.find(query, FonteInformacaoDTO.class, FONTE_INFORMACOES);
	}

	public List<FonteInformacaoDTO> getFontesInformacoes(TipoFonte tipoFonte) {
		Query query = new Query(Criteria.where("tipoFonte").is(tipoFonte));
		return mongoOps.find(query, FonteInformacaoDTO.class, FONTE_INFORMACOES);
	}

}
