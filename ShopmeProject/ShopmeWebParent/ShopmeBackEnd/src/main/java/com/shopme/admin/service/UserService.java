package com.shopme.admin.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.shopme.admin.error.UserNotFoundException;
import com.shopme.common.model.User;

public interface UserService {
	List<User> ListAll();
	Page<User> listByPage(int pageNum);
	User saveUser(User user);
	boolean isEmailUnique(String email, Long id);
	User get(Long id) throws UserNotFoundException;
	void delete(Long id) throws UserNotFoundException;
	void updateUserEnabledStatus(Long id, boolean enabled);
}
