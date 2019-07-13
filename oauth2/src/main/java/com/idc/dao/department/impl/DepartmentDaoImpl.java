package com.idc.dao.department.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.idc.dao.base.UserBaseDao;
import com.idc.dao.department.DepartmentDao;
import com.idc.module.department.Department;

@Repository("departmentDao")
public class DepartmentDaoImpl extends UserBaseDao implements DepartmentDao {

	@Override
	public List<Department> queryByEmpId(int empId) {
		return getSqlSession().selectList("dept.queryByEmpId", empId);
	}

}
