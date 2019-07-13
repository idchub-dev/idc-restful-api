package com.idc.service.oauth;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.idc.dao.department.DepartmentDao;
import com.idc.dao.employee.EmployeeDao;
import com.idc.dao.role.RoleDao;
import com.idc.module.department.Department;
import com.idc.module.employee.Employee;
import com.idc.module.role.Role;
import com.util.StringUtil;

@Service("userDetailsService")
public class IdcUserDetailsService implements UserDetailsService {

	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private DepartmentDao departmentDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Employee emp = employeeDao.queryByUserName(username);
		if(emp == null) {
			throw new UsernameNotFoundException("用户名密码错误");
		}
		List<Department> deptList = departmentDao.queryByEmpId(emp.getId());
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		int roleId = emp.getRoleId();
		if(roleId > 0) {
			Role role = roleDao.queryById(roleId);
			if(!(role == null || StringUtil.isBlank(role.getGrantedAuthority()))) {
				Set<String> set = StringUtils.commaDelimitedListToSet(role.getGrantedAuthority());
				for(String grantStr : set) {
					authorities.add(new SimpleGrantedAuthority("ROLE_" + grantStr));
				}
			}
		}
		if(!CollectionUtils.isEmpty(deptList)) {
			for(Department dept : deptList) {
				String grantedAuthority = dept.getGrantedAuthority();
				if(StringUtil.isNotBlank(grantedAuthority)) {
					Set<String> set = StringUtils.commaDelimitedListToSet(grantedAuthority);
					for(String grantStr : set) {
						authorities.add(new SimpleGrantedAuthority("ROLE_" + grantStr));
					}
				}
			}
		}
		emp.setAuthorities(authorities);
		return emp;
	}

}
