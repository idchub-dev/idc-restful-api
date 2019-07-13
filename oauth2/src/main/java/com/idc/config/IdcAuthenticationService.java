package com.idc.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public interface IdcAuthenticationService {
	
	public Authentication auth(String username,String password) throws AuthenticationException;
}
