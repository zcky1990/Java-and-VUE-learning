package app.environment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentBuild {
	@Value("${build.env}")	
	private String environtment;

	@Value("${build.url}")	
	private String baseUrl;

	@Value("${mail.username}")	
	private String mailUsername;
	@Value("${mail.password}")	
	private String mailPassword;
	@Value("${mail.port}")	
	private String mailPort;
	@Value("${mail.host}")	
	private String mailHost;

	@Value("${cloudinary.cloud_name}")
	private String cloudinaryCloudName;
	@Value("${cloudinary.api_key}")
	private String cloudinaryApiKey;
	@Value("${cloudinary.api_secret}")
	private String cloudinaryApiSecret;
	@Value("${cloudinary.environment_variable}")
	private String cloudinaryEnvironmentVariable;

	public String getBaseUrl() {
		return baseUrl;
	}
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	public String getEnvirontment() {
		return environtment;
	}
	public void setEnvirontment(String environtment) {
		this.environtment = environtment;
	}
	public String getMailUsername() {
		return mailUsername;
	}
	public void setMailUsername(String mailUsername) {
		this.mailUsername = mailUsername;
	}
	public String getMailPassword() {
		return mailPassword;
	}
	public void setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
	}
	public String getMailPort() {
		return mailPort;
	}
	public void setMailPort(String mailPort) {
		this.mailPort = mailPort;
	}
	public String getMailHost() {
		return mailHost;
	}
	public void setMailHost(String mailHost) {
		this.mailHost = mailHost;
	}
	public String getCloudinaryCloudName() {
		return cloudinaryCloudName;
	}
	public void setCloudinaryCloudName(String cloudinaryCloudName) {
		this.cloudinaryCloudName = cloudinaryCloudName;
	}
	public String getCloudinaryApiKey() {
		return cloudinaryApiKey;
	}
	public void setCloudinaryApiKey(String cloudinaryApiKey) {
		this.cloudinaryApiKey = cloudinaryApiKey;
	}
	public String getCloudinaryApiSecret() {
		return cloudinaryApiSecret;
	}
	public void setCloudinaryApiSecret(String cloudinaryApiSecret) {
		this.cloudinaryApiSecret = cloudinaryApiSecret;
	}
	public String getCloudinaryEnvironmentVariable() {
		return cloudinaryEnvironmentVariable;
	}
	public void setCloudinaryEnvironmentVariable(String cloudinaryEnvironmentVariable) {
		this.cloudinaryEnvironmentVariable = cloudinaryEnvironmentVariable;
	}
}