package com.idc.dao.oauth.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.idc.dao.base.OauthBaseDao;
import com.idc.dao.oauth.TokenDao;
import com.idc.module.oauth.OauthAccessToken;
import com.idc.module.oauth.OauthRefreshToken;

@Repository("tokenDao")
public class TokenDaoImpl extends OauthBaseDao implements TokenDao {

	@Override
	public OauthAccessToken queryByTokenId(String tokenId) {
		return getSqlSession().selectOne("token.queryByTokenId", tokenId);
	}

	@Override
	public OauthAccessToken queryByAuthenticationId(String authenticationId) {
		return getSqlSession().selectOne("token.queryByAuthenticationId", authenticationId);
	}

	@Override
	public List<OauthAccessToken> queryByClientIdAndUserName(String clientId, String username) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("clientId", clientId);
		param.put("userName", username);
		return getSqlSession().selectList("token.queryByClientIdAndUserName", param);
	}

	@Override
	public List<OauthAccessToken> queryByClientId(String clientId) {
		return getSqlSession().selectList("token.queryByClientId", clientId);
	}

	@Override
	public void deleteByTokenId(String tokenId) {
		getSqlSession().delete("token.deleteByTokenId", tokenId);
	}

	@Override
	public void deleteByRefreshToken(String refreshToken) {
		getSqlSession().delete("token.deleteByRefreshToken", refreshToken);
	}

	@Override
	public void insert(OauthAccessToken accessToken) {
		getSqlSession().insert("token.insertAccessToken", accessToken);
	}

	@Override
	public void insert(OauthRefreshToken refreshToken) {
		getSqlSession().insert("token.insertRefreshToken", refreshToken);
	}

	@Override
	public OauthRefreshToken queryRefreshTokenByTokenId(String tokenId) {
		return getSqlSession().selectOne("token.queryRefreshTokenByTokenId", tokenId);
	}

	@Override
	public void deleteRefreshByTokenId(String tokenId) {
		getSqlSession().delete("token.deleteRefreshByTokenId", tokenId);
	}

}