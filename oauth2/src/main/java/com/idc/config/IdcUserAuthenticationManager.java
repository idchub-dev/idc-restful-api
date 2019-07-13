package com.idc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.stereotype.Service;

import com.idc.module.employee.Employee;
import com.util.AESUtil;
import com.util.StringUtil;

@Service("idcAuthenticationService")
public class IdcUserAuthenticationManager implements IdcAuthenticationService{

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	public Authentication auth(String username, String password)
			throws AuthenticationException {
		
		if(StringUtil.isEmpty(username) || StringUtil.isEmpty(password)) {
			throw new InvalidGrantException("用户名密码错误");
		}
		UserDetails user = userDetailsService.loadUserByUsername(username);
		
		if(!AESUtil.encrypt(password).equals(user.getPassword())) {
			throw new BadCredentialsException("用户名密码错误");
		}
		
		if(!(user instanceof Employee)) {
			throw new RuntimeException("用户名密码错误");
		}
		
		Employee emp = (Employee) user;
		
		if(emp.getStatus() == 0) {
			throw new InvalidGrantException("您的账号已被锁定，请与管理员联系");
		}
		return new IdcAuthenticationToken(username, "", emp.getAuthorities());
	}

}
