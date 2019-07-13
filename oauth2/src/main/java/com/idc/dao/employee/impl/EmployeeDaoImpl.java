package com.idc.dao.employee.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.idc.dao.base.UserBaseDao;
import com.idc.dao.employee.EmployeeDao;
import com.idc.module.employee.Employee;

@Repository("employeeDao")
public class EmployeeDaoImpl extends UserBaseDao implements EmployeeDao{

	@Override
	public Employee login(String username, String password) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("username", username);
		param.put("password", password);
		return getSqlSession().selectOne("employee.login", param);
	}

	@Override
	public Employee queryByUserName(String username) {
		return getSqlSession().selectOne("employee.queryByUserName", username);
	}

}
