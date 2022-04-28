package com.shopme.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopme.common.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
	
}
