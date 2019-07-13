package com.idc.config;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.idc.module.employee.Employee;

public class IdcAuthenticationToken extends UsernamePasswordAuthenticationToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6986430773078852928L;

	public IdcAuthenticationToken(Object principal, Object credentials) {
		super(principal, credentials);
	}

	public IdcAuthenticationToken(Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
	}

	@Override
	public String getName() {
		if(this.getPrincipal() instanceof Employee) {
			return ((Employee)this.getPrincipal()).getUsername();
		}
		return super.getName();
	}

}
