package com.idc.dao.base;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.support.SqlSessionDaoSupport;

public class OauthBaseDao extends SqlSessionDaoSupport {

	private final Logger logger = LogManager.getLogger(OauthBaseDao.class);
	
	@Resource(name="oauthSqlSessionFactory")
	public void initSqlSessionFactory(SqlSessionFactory oauthSqlSessionFactory) {
		super.setSqlSessionFactory(oauthSqlSessionFactory);
		logger.info("Init sqlsession factory......");
	}
}