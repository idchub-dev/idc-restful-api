package com.test.oauth;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

public class OauthTest {
	
	public final String BASE_URL = "http://localhost:8088/oauth2";
	
	@Test
	public void testLogin() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
				if(response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
					super.handleError(response);
				}
			}
		});
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<String, String>();
		formData.add("username", "admin");
		formData.add("password", "admin123");
		formData.add("grant_type", "password");
		formData.add("client_id", "idc");
		formData.add("client_secret", "123456");
		formData.add("scope", "web");
		HttpHeaders headers = new HttpHeaders();
		//ResponseEntity<Map> response = restTemplate.postForEntity(BASE_URL + "/oauth/token", formData, Map.class);
		@SuppressWarnings("unchecked")
		Map<String,Object> map = restTemplate.exchange(BASE_URL + "/oauth/token", HttpMethod.POST,
				new HttpEntity<MultiValueMap<String, String>>(formData, headers), Map.class).getBody();
		System.out.println(map);
		//System.out.println(response.getBody());
	}
}
