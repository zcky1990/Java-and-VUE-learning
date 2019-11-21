package com.webapps.app.model;

import org.springframework.beans.factory.annotation.Value;

public class Environtment {
	
	@Value("${jwt.environtment}")
	private String environtment;
	
	public String getEnvirontment() {
		return environtment;
	}
}
