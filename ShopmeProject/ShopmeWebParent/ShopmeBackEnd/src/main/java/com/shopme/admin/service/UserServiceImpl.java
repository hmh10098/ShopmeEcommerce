package com.shopme.admin.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopme.admin.error.UserNotFoundException;
import com.shopme.admin.repository.UserRepository;
import com.shopme.common.model.User;
import org.springframework.data.domain.Pageable;

@Service
@Transactional
public class UserServiceImpl implements UserService{

	public static final int USERS_PER_PAGE = 4;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public List<User> ListAll(){
		return (List<User>) userRepository.findAll();
	}
	
	@Override
	public Page<User> listByPage(int pageNum){
		Pageable pageable = PageRequest.of(pageNum - 1, USERS_PER_PAGE);
		return userRepository.findAll(pageable);
	}

	
	@Override
	public User saveUser(User user) {
		boolean isUpdatingUser = (user.getId() != null);
		
		if (isUpdatingUser) {
			User existUser = userRepository.findById(user.getId()).get();
			if (user.getPassword().isEmpty()) {
				user.setPassword(existUser.getPassword());
			}
			else {
				encodePassword(user);
			}
		}
		else {
			encodePassword(user);
		}
		return userRepository.save(user);
	}
	
	private void encodePassword(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
	}
	
	@Override
	public boolean isEmailUnique(String email, Long id) {
		User userByEmail = userRepository.getUserByEmail(email);
		
		if (userByEmail == null) return true;
		
		boolean isCreatingNew = (id == null);
		
		if (isCreatingNew) {
			if (userByEmail != null) return false;
		} else {
			if (userByEmail.getId() != null) {
				if (userByEmail.getId() != id) {
				return false;
				}
			}
		}
		
		return true;
	}
	
	@Override
	public User get(Long id) throws UserNotFoundException {
		try {
		return userRepository.findById(id).get();
		} catch (Exception ex) {
			throw new UserNotFoundException("Could not find any user with ID" + id);
		}
	}
	
	@Override
	public void delete(Long id) throws UserNotFoundException {
		Long countById = userRepository.countById(id);
		if (countById == null || countById == 0) {
			throw new UserNotFoundException("Count not find any user with ID "+ id);
		}
		else {
			userRepository.deleteById(id);
		}
	}
	
	@Override
	public void updateUserEnabledStatus(Long id, boolean enabled) {
		userRepository.updateEnabledStatus(id, enabled);
	}

	
}
