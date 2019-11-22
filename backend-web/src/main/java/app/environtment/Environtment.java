package app.environtment;

import org.springframework.beans.factory.annotation.Value;

public class Environtment {
	@Value("${build.env}")
	private String environtment;
	
	public String getEnvirontment() {
		return environtment;
	}
}
