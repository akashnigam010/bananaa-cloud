package in.socyal;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import in.socyal.sc.Application;
import in.socyal.sc.api.login.request.LoginRequest;
import in.socyal.sc.api.login.response.LoginResponse;

/*@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})*/
public class SecurityConfigTest /*extends AbstractRestClient */{/*
	@Autowired
	private TestRestTemplate testRestTemplate;
	@LocalServerPort
	private int port;

	@Value("${local.management.port}")
	private int mgt;
	
	@Before
	public void setUp() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.<MediaType>asList(MediaType.APPLICATION_JSON));
		context = "http://localhost:" + this.port;
	}
	
	@Test
	public void shouldReturn200WhenSendingRequestToController() throws Exception {
		ResponseEntity<String> entity = this.testRestTemplate.getForEntity(
				"http://localhost:" + this.port + "/", String.class);
		Assert.assertTrue(entity.getStatusCode().equals(HttpStatus.OK));
	}
	
	@Test
	public void shouldReturn401WhenSendingRequestToController() throws Exception {
		URL url = new URL(context+"/manage");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		int responseCode = conn.getResponseCode();
		System.out.println(responseCode);
	}
	
	@Test
	public void shouldGetFbToken() throws Exception {
		LoginRequest request = new LoginRequest();
		request.setFbId("1170542099667838");
		request.setFbAccessToken("EAAJLSgyq0bUBAE97lGYEAEhQXwEvgxvZAKYIT6cXY5SLczfJPcV7BvJSAS5K7ule6zpsZBZBuOuHb53quiPZAsytduZAzsztYzzMARAjZBclSfzYcEsXHLk8J19D5oVL9M7r3V7j8QoGuqRkHB5hR0LSul91PBkvlyC9ctjDz8Tvmfr5GJ2v8imZCYNAoC3lHDJML9cZCBKqt7uRuq5wAauC");
		LoginResponse loginResp = invokePost("/socyal/login/fbLogin", null, null, request, LoginResponse.class);
		Assert.assertNotNull(loginResp.getAccessToken());
	}
	
	@Test
	public void shouldSkipLogin() throws Exception {
		LoginResponse loginResp = invokeGet("/socyal/login/skipLogin", null, null, LoginResponse.class);
		Assert.assertNotNull(loginResp.getAccessToken());
	}
*/}
