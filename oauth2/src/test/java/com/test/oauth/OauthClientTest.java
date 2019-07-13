package com.test.oauth;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

public class OauthClientTest {

	public final String BASE_URL = "http://localhost:8088/oauth2";
	
	private String access_token;
	
	private RestTemplate restTemplate;
	
	private String clientId = "idc";
	
	private String clientSecret = "123456";
	
	@Before
	public void testAuthClient() {
		restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
				if(response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
					super.handleError(response);
				}
			}
		});
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<String, String>();
		formData.add("grant_type", "client_credentials");
		formData.add("client_id", clientId);
		formData.add("client_secret", clientSecret);
		formData.add("scope", "web");
		HttpHeaders headers = new HttpHeaders();
		
		@SuppressWarnings("unchecked")
		Map<String,Object> map = restTemplate.exchange(BASE_URL + "/oauth/token", HttpMethod.POST,
				new HttpEntity<MultiValueMap<String, String>>(formData, headers), Map.class).getBody();
		System.out.println(map);
		
		Assert.assertTrue(map.containsKey("access_token"));
		
		access_token = map.get("access_token").toString();
	}
	
	@Test
	public void testCheckToken() {
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<String, String>();
		formData.add("token", access_token);
		HttpHeaders headers = new HttpHeaders();
		
		String creds = String.format("%s:%s", clientId, clientSecret);
		try {
			headers.add("Authorization", "Basic " + new String(Base64Utils.encode(creds.getBytes("UTF-8"))));
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("Could not convert String");
		}
		
		@SuppressWarnings("unchecked")
		Map<String,Object> map = restTemplate.exchange(BASE_URL + "/oauth/check_token", HttpMethod.POST,
				new HttpEntity<MultiValueMap<String, String>>(formData, headers), Map.class).getBody();
		Assert.assertFalse(map.containsKey("error"));
		System.out.println(map);
	}
}
