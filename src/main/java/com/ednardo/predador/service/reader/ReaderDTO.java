package com.ednardo.predador.service.reader;

public class ReaderDTO {

	private String clube;

	private String url;

	public ReaderDTO(String clube, String url) {
		super();
		this.clube = clube;
		this.url = url;
	}

	public String getClube() {
		return clube;
	}

	public void setClube(String clube) {
		this.clube = clube;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
