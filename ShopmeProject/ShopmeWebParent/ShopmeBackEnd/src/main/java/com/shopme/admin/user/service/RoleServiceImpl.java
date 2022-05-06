package com.shopme.admin.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.admin.user.repository.RoleRepository;
import com.shopme.common.model.Role;

@Service
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	RoleRepository roleRepository;
	
	@Override
	public List<Role> listAllRole(){
		return roleRepository.findAll();
	}
}
