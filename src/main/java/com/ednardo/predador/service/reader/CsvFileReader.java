package com.ednardo.predador.service.reader;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ashraf_sarhan
 *
 */
public class CsvFileReader {

	// CSV file header
	private static final String[] FILE_HEADER_MAPPING = { "clube", "url" };

	// Student attributes
	private static final String CLUBE = "clube";
	private static final String URL = "url";

	private static Logger log = LoggerFactory.getLogger(CsvFileReader.class);

	public static List<ReaderDTO> readCsvFile(String fileName) {
		FileReader fileReader = null;
		CSVParser csvFileParser = null;
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER_MAPPING);
		try {
			String path = Paths.get(CsvFileReader.class.getResource(fileName).toURI()).toString();
			fileReader = new FileReader(path);
			csvFileParser = new CSVParser(fileReader, csvFileFormat);
			List<CSVRecord> csvRecords = csvFileParser.getRecords();
			return convertToFonteInformacaoDTO(csvRecords);
		} catch (Exception e) {
			log.error("Erro ao converter arquivo csv: " + fileName, e);
		} finally {
			try {
				fileReader.close();
				csvFileParser.close();
			} catch (IOException e) {
				log.error("Erro ao fechar fileReader/csvFileParser: " + fileName, e);
			}
		}
		return new ArrayList<ReaderDTO>();
	}

	private static List<ReaderDTO> convertToFonteInformacaoDTO(List<CSVRecord> csvRecords) {
		List<ReaderDTO> fontes = new ArrayList<ReaderDTO>();
		for (int i = 1; i < csvRecords.size(); i++) {
			CSVRecord record = csvRecords.get(i);
			ReaderDTO fonte = new ReaderDTO(record.get(CLUBE), record.get(URL));
			fontes.add(fonte);
		}
		return fontes;
	}
}
