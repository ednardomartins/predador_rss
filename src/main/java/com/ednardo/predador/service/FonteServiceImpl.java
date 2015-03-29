package com.ednardo.predador.service;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ednardo.predador.dto.FonteInformacaoDTO;
import com.ednardo.predador.dto.TipoFonte;
import com.ednardo.predador.model.RepositoryFonte;
import com.ednardo.predador.model.mongo.security.HashGenerate;
import com.ednardo.predador.service.reader.CsvFileReader;
import com.ednardo.predador.service.reader.ReaderDTO;

@Component
public class FonteServiceImpl {

	@Resource
	@Qualifier("repositoryFonte")
	private RepositoryFonte repository;

	private static Logger log = Logger.getLogger(FonteServiceImpl.class);

	public void carregaFontes(String arquivoCsv, TipoFonte tipoFonte) {
		List<ReaderDTO> dtos = CsvFileReader.readCsvFile(arquivoCsv);
		for (ReaderDTO readerDTO : dtos) {
			FonteInformacaoDTO fonteInformacao = new FonteInformacaoDTO();
			try {
				fonteInformacao.setIdFonte(HashGenerate.hashString(readerDTO.getUrl()));
			} catch (Exception e) {
				log.error("erro a gerar o hash id", e);
			}
			fonteInformacao.setNomeClube(readerDTO.getClube());
			fonteInformacao.setUrl(readerDTO.getUrl());
			fonteInformacao.setTipoFonte(tipoFonte);
			repository.insereFonteInformacao(fonteInformacao);
		}
	}

	public void carregaFontes(File file, TipoFonte tipoFonte) {
		List<ReaderDTO> dtos = CsvFileReader.readCsvFile(file);
		for (ReaderDTO readerDTO : dtos) {
			FonteInformacaoDTO fonteInformacao = new FonteInformacaoDTO();
			try {
				fonteInformacao.setIdFonte(HashGenerate.hashString(readerDTO.getUrl()));
			} catch (Exception e) {
				log.error("erro a gerar o hash id", e);
			}
			fonteInformacao.setNomeClube(readerDTO.getClube());
			fonteInformacao.setUrl(readerDTO.getUrl());
			fonteInformacao.setTipoFonte(tipoFonte);
			repository.insereFonteInformacao(fonteInformacao);
		}

	}
}
