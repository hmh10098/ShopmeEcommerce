package com.shopme.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopme.common.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
