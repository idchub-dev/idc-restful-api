package com.idc.dao.base;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.support.SqlSessionDaoSupport;

public class UserBaseDao extends SqlSessionDaoSupport {

	private final Logger logger = LogManager.getLogger(UserBaseDao.class);
	
	@Resource(name="userSqlSessionFactory")
	public void initSqlSessionFactory(SqlSessionFactory userSqlSessionFactory) {
		super.setSqlSessionFactory(userSqlSessionFactory);
		logger.info("Init sqlsession factory......");
	}
}