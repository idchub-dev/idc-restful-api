package com.idc.dao.employee;

import com.idc.module.employee.Employee;

public interface EmployeeDao {

	public Employee login(String username,String password);
	
	public Employee queryByUserName(String username);
}
