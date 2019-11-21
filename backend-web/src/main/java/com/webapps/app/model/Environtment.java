package com.webapps.app.model;

import com.webapps.app.constants.Constant;

public class Environtment {
	private String environtment;
	
	public Environtment() {
		environtment = Constant.PRODUCTION_ENV;
	}

	public String getEnvirontment() {
		return environtment;
	}
}
