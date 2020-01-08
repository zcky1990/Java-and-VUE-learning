package app.util;

import java.io.File;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import app.config.MailConfig;
import app.constants.Constant;
import app.environment.EnvironmentBuild;
import app.mongo.model.Users;

public class MailUtility {
	private Util util = new Util();
	private EnvironmentBuild env;
	private MailConfig config;

	public MailUtility(EnvironmentBuild env) {
		this.env = env;
	}

	public void sendMail(String messageBody, String pathAttachment, String to, String subject) {
		Properties prop = new Properties();
		prop.put("mail.smtp.host", env.getMailHost());
        prop.put("mail.smtp.port", env.getMailPort());
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", env.getMailPort());
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        
		Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(env.getMailUsername(), env.getMailPassword());
                    }
                });
		
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(config.getUsername()));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);
			Multipart multipart = new MimeMultipart();

			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent(messageBody, "text/html");

			if(!pathAttachment.equals("")) {
				MimeBodyPart attachmentBodyPart = new MimeBodyPart();
				attachmentBodyPart.attachFile(new File(pathAttachment));
				multipart.addBodyPart(attachmentBodyPart);
			}
			multipart.addBodyPart(mimeBodyPart);
			message.setContent(multipart);
			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getEmailTemplate(String url) {
		String mailBody = util.getStringJsonFile(url);
		return mailBody;
	}

	public String getVerificationMailMessageBody(String template, Users users) {
		template = template.replace("{{username}}", users.getUsername());
		String verificationUrl = getBaseUrl()+"user/validation/"+ users.getStringId();
		template = template.replace("{{url}}", verificationUrl);
		return template;
	}

	public String getBaseUrl() {
		if(env.equals(Constant.DEV_ENV)){
			return Constant.BASE_URL_DEV;
		}else {
			return Constant.BASE_URL_PROD;
		}
	}
}
