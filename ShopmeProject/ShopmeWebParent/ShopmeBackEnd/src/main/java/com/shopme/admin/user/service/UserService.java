package com.shopme.admin.user.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.shopme.admin.user.error.UserNotFoundException;
import com.shopme.common.model.User;

public interface UserService {
	List<User> ListAll();
	Page<User> listByPage(int pageNum, String sortField, String sortOder, String keyword);
	User saveUser(User user);
	boolean isEmailUnique(String email, Long id);
	User get(Long id) throws UserNotFoundException;
	void delete(Long id) throws UserNotFoundException;
	void updateUserEnabledStatus(Long id, boolean enabled);
	User getByEmail(String email);
	User updateAccount(User userInForm);
}
