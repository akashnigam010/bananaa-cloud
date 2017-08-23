package in.socyal.sc.helper.mail;

import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;

import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.login.request.MLoginRequest;
import in.socyal.sc.api.manage.request.MessageRequest;

@Component
public class MailSender {
	private ResourceBundle resource = ResourceBundle.getBundle("bananaa-application");
	private static final String EMAIL_FROM = "email.from";
	private static final String EMAIL_FROM_ALIAS = "email.from.alias";
	private static final String EMAIL_PASSWORD = "email.password";
	private static final String EMAIL_SUBJECT_CONTACT_US = "email.subject";
	private static final String EMAIL_SUBJECT_PASSWORD_RESET = "email.password.reset.subject";
	private static final String EMAIL_TO = "email.to";

	public void sendEmail(MessageRequest request) throws BusinessException {
		Session session = getSession();
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(resource.getString(EMAIL_FROM_ALIAS)));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(resource.getString(EMAIL_TO)));
			message.setSubject(resource.getString(EMAIL_SUBJECT_CONTACT_US));
			message.setText("Name : " + request.getName() + ", Phone : " + request.getPhone() + ", Email : "
					+ request.getEmail() + ", Message : " + request.getMessage());
			Transport.send(message);
		} catch (MessagingException e) {
			throw new BusinessException();
		}
	}

	public void sendPasswordMail(MLoginRequest request) throws BusinessException {
		Session session = getSession();
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(resource.getString(EMAIL_FROM_ALIAS)));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(request.getEmail()));
			message.setContent(
					"Hello " + request.getName() + ", <br /><br /> Here is your password to login to Bananaa: <b>"
							+ request.getPassword()
							+ "</b><br /><br />Best, <br />Team Bananaa<br />https://www.bananaa.in",
					"text/html; charset=utf-8");
			message.setSubject(resource.getString(EMAIL_SUBJECT_PASSWORD_RESET));
			Transport.send(message);
		} catch (MessagingException e) {
			throw new BusinessException();
		}
	}

	private Properties getProperties() {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		return props;
	}
	
	private Session getSession() {
		Properties props = getProperties();
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(resource.getString(EMAIL_FROM),
						resource.getString(EMAIL_PASSWORD));
			}
		});
		return session;
	}
}
