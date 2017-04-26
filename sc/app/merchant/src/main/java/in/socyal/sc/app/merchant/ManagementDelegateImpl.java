package in.socyal.sc.app.merchant;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import in.socyal.sc.api.SearchRequest;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.manage.request.AddItemRequest;
import in.socyal.sc.api.manage.request.AddRequest;
import in.socyal.sc.api.manage.request.MessageRequest;
import in.socyal.sc.api.manage.response.GetCuisinesResponse;
import in.socyal.sc.api.manage.response.GetItemImagesResponse;
import in.socyal.sc.api.manage.response.GetSuggestionsResponse;
import in.socyal.sc.persistence.ManagementDao;

@Service
public class ManagementDelegateImpl implements ManagementDelegate {

	@Autowired
	ManagementDao dao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void addItem(AddItemRequest request) throws BusinessException {
		request.setName(WordUtils.capitalizeFully(request.getName()));
		request.setNameId(createNameId(request.getName()));
		dao.addItem(request);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void addCuisine(AddRequest request) {
		dao.addCuisine(request);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void addSuggestion(AddRequest request) {
		dao.addSuggestion(request);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public GetCuisinesResponse getCuisines(SearchRequest request) {
		GetCuisinesResponse response = new GetCuisinesResponse();
		response.setCuisines(dao.getCuisines(request));
		return response;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public GetSuggestionsResponse getSuggestions(SearchRequest request) {
		GetSuggestionsResponse response = new GetSuggestionsResponse();
		response.setSuggestions(dao.getSuggestions(request));
		return response;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public GetItemImagesResponse getItemImages(SearchRequest request) {
		GetItemImagesResponse response = new GetItemImagesResponse();
		response.setImages(dao.getItemImages(request));
		return response;
	}

	private String createNameId(String name) {
		String[] nameSegments = name.split(" ");
		StringBuilder nameId = new StringBuilder();
		int i;
		for (i = 0; i < nameSegments.length - 1; i++) {
			nameId.append(nameSegments[i].toLowerCase() + "-");
		}
		nameId.append(nameSegments[i].toLowerCase());
		return nameId.toString();
	}

	@Override
	public void contactUsMessage(MessageRequest request) throws BusinessException {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("akashnigam020@gmail.com", "Akash123!");
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("akashnigam020@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("bananaa@bananaa.in"));
			message.setSubject("Contact request from : " + request.getName());
			message.setText("Phone : " + request.getPhone() +", Email : " + request.getEmail() + ", Message : " + request.getMessage());

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new BusinessException();
		}
	}
}
