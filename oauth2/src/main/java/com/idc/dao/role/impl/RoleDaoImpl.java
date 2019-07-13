package com.idc.dao.role.impl;

import org.springframework.stereotype.Repository;

import com.idc.dao.base.UserBaseDao;
import com.idc.dao.role.RoleDao;
import com.idc.module.role.Role;

@Repository("roleDao")
public class RoleDaoImpl extends UserBaseDao implements RoleDao {

	@Override
	public Role queryById(int roleId) {
		return getSqlSession().selectOne("role.queryById", roleId);
	}

}
