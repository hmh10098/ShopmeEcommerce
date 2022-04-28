package com.shopme.admin.service;

import java.util.List;


import com.shopme.common.model.User;

public interface UserService {
	List<User> ListAll();
	void saveUser(User user);
}
