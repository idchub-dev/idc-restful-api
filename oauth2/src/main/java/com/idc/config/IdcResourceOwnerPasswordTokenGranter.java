package com.idc.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

public class IdcResourceOwnerPasswordTokenGranter extends ResourceOwnerPasswordTokenGranter {

	private final Logger logger = LogManager.getLogger(IdcResourceOwnerPasswordTokenGranter.class);

	private final IdcAuthenticationService idcAuthenticationService;;
	
	public IdcResourceOwnerPasswordTokenGranter(IdcAuthenticationService idcAuthenticationService,
			AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService,
			OAuth2RequestFactory requestFactory) {
		super(null, tokenServices, clientDetailsService, requestFactory);
		this.idcAuthenticationService = idcAuthenticationService;
	}

	@Override
	protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {

		Map<String, String> parameters = new LinkedHashMap<String, String>(tokenRequest.getRequestParameters());
		String username = parameters.get("username");
		String password = parameters.get("password");
		Authentication userAuth = null;
		parameters.remove("password");
		try {
			userAuth = idcAuthenticationService.auth(username, password);
		} catch (AccountStatusException ase) {
			throw new InvalidGrantException(ase.getMessage());
		} catch (BadCredentialsException e) {
			throw new InvalidGrantException(e.getMessage());
		}
		if (userAuth == null || !userAuth.isAuthenticated()) {
			throw new InvalidGrantException("Could not authenticate user: " + parameters.get("username"));
		}
		
		OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);		
		return new OAuth2Authentication(storedOAuth2Request, userAuth);
	}

}