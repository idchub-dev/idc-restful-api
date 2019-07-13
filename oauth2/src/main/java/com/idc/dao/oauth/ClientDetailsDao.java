package com.idc.dao.oauth;

import com.idc.module.oauth.OauthClientDetails;

public interface ClientDetailsDao {
	
	public OauthClientDetails queryByClientId(String clientId);
}