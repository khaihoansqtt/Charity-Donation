package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dto.UserDTO;
import com.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	@Autowired
	HttpSession session;
	@Autowired
	UserService userService;
	
	@GetMapping("/")
	public String home() {
		return "admin/login";
	}
	
	@PostMapping("/login")
	public String login(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {
		
		UserDTO userDTO = userService.login(email, password);
		
		if (userDTO == null) {	// Nếu userDTO null thì về lại trang login
			model.addAttribute("error", "");
			return "admin/login";
		} else {
			if (userDTO.getRoleId() == 1) {	// Nếu là admin thì về trang admin home
				session.setAttribute("loginedUser", userDTO);
				return "redirect:/admin";
			} else {
				if (userDTO.getStatus() == 1) {		// Nếu là user và được cấp phép (status == 1) thì về trang user home
					session.setAttribute("loginedUser", userDTO);
					return "redirect:/user";
				} else {
					model.addAttribute("locked", "");	// Nếu là user bị khóa thì về trang đăng nhập
					return "admin/login";
				}
			}
		}
	}
	
	@GetMapping("/logout")
	public String logout() {
		session.removeAttribute("loginedUser");
		return "admin/login";
	}
}
