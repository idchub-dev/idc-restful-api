package com.idc.dao.department;

import java.util.List;

import com.idc.module.department.Department;

public interface DepartmentDao {

	public List<Department> queryByEmpId(int empId);
}
