package com.shopme.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.service.RoleService;
import com.shopme.admin.service.UserService;
import com.shopme.common.model.Role;
import com.shopme.common.model.User;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@GetMapping("/users")
	public String listAll(Model model) {
		List<User> listUsers = userService.ListAll();
		model.addAttribute("listUsers", listUsers);
		return "users";
	}
	
	@GetMapping("/users/new")
	public String newUser(Model model) {
		List<Role> listRoles = roleService.listAllRole();
	
		User user = new User();
		user.setEnabled(true);
		model.addAttribute("user", user);
		model.addAttribute("listRoles", listRoles);
		return "user_form";
	}
	
	@PostMapping("/users/save")
	public String saveUser(User user, RedirectAttributes redirectAttributes) {
		System.out.println(user);
		userService.saveUser(user);
		
		redirectAttributes.addFlashAttribute("message", "The user has been save successfully");
		return "redirect:/users";
	}
}
