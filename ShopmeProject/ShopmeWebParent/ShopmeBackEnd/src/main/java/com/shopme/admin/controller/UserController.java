package com.shopme.admin.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.service.FileUploadUtil;
import com.shopme.admin.service.RoleService;
import com.shopme.admin.service.UserService;
import com.shopme.admin.service.UserServiceImpl;
import com.shopme.common.model.Role;
import com.shopme.common.model.User;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@GetMapping("/users")
	public String listFirstPage(Model model) {
		return listByPage(1, model);
	}
	
	@GetMapping("/users/new")
	public String newUser(Model model) {
		List<Role> listRoles = roleService.listAllRole();
	
		User user = new User();
		user.setEnabled(true);
		model.addAttribute("user", user);
		model.addAttribute("listRoles", listRoles);
		model.addAttribute("pageTitle", "Create New User");
		return "user_form";
	}
	
	@PostMapping("/users/save")
	public String saveUser(User user, RedirectAttributes redirectAttributes,
			@RequestParam("image") MultipartFile multipartFile) throws IOException {
		if (!multipartFile.isEmpty()) {	
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());	
			user.setPhotos(fileName);
			User savedUser = userService.saveUser(user);
			String uploadDir = "user-photos/" + savedUser.getId();
			
			FileUploadUtil.cleanDirectory(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		}
		else {
			if (user.getPhotos().isEmpty()) user.setPhotos(null);
			userService.saveUser(user);
		}
		
		redirectAttributes.addFlashAttribute("message", "The user has been save successfully");
		return "redirect:/users";
	}
	
	@GetMapping("/users/page/{pageNum}")
	public String listByPage(@PathVariable(name="pageNum") int pageNum, Model model) {
		Page<User> pageUser = userService.listByPage(pageNum);
		
		long startCount = (pageNum - 1) * UserServiceImpl.USERS_PER_PAGE + 1;
		long endCount = startCount + UserServiceImpl.USERS_PER_PAGE;
		if (endCount > pageUser.getTotalElements()) {
			endCount = pageUser.getTotalElements();
		}
		model.addAttribute("listUsers", pageUser.getContent());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", pageUser.getTotalPages());
		model.addAttribute("totalItems", pageUser.getTotalElements());
		return "users";
	}
	
	@GetMapping("/users/edit/{id}")
	public String editUser(@PathVariable(name="id") Long id, RedirectAttributes redirectAttributes
			,Model model) {
		
		List<Role> listRoles = roleService.listAllRole();
		model.addAttribute("listRoles", listRoles);
		model.addAttribute("pageTitle", "Edit User (ID: " + id + ")");
		try {
			User user = userService.get(id);
			model.addAttribute("user", user);
			return "user_form";
		} catch(Exception ex){
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
			return "redirect:/users";
		}
	}
	
	@GetMapping("/users/delete/{id}")
	public String deleteUser(@PathVariable(name="id") Long id, RedirectAttributes redirectAttributes
			) {
		try {
			userService.delete(id);
			redirectAttributes.addFlashAttribute("message", "The user ID "+ id + " has been deleted successfully.");
		} catch(Exception ex){
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
		}
		return "redirect:/users";
	}
	
	@GetMapping("/users/{id}/enabled/{status}")
	public String updateEnableStatus(@PathVariable(name="id") Long id, @PathVariable(name="status") boolean status
			, RedirectAttributes redirectAttributes) {
		
		userService.updateUserEnabledStatus(id, status);
		String message = status ? "enabled" : "disabled";
		String fMessage = "The user ID " + id + " has been " + message;
		redirectAttributes.addFlashAttribute("message", fMessage);
		return "redirect:/users";
	}
}
