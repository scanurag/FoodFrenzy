package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entities.User;
import com.example.demo.services.UserServices;

@Controller
public class UserController {

	@Autowired
	private UserServices services;

	@GetMapping(value = {"/register", "/register.html"})
	public String registerPage(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("userRegistration", user);
		return "register";
	}

	@PostMapping("/register")
	public String registerUser(@ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
		model.addAttribute("userRegistration", user);

		// Check for field binding/type errors (e.g. non-numeric phone number)
		if (bindingResult.hasErrors()) {
			model.addAttribute("error", "Please provide a valid 10-digit phone number.");
			return "register";
		}

		// Validate required fields
		if (user.getUname() == null || user.getUname().trim().isEmpty() ||
		    user.getUemail() == null || user.getUemail().trim().isEmpty() ||
		    user.getUpassword() == null || user.getUpassword().trim().isEmpty() ||
		    user.getUnumber() == null) {
			model.addAttribute("error", "All fields are required.");
			return "register";
		}

		// Validate email format
		String email = user.getUemail().trim();
		if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
			model.addAttribute("error", "Please enter a valid email address.");
			return "register";
		}

		// Validate phone number length (10 digits)
		String phoneStr = String.valueOf(user.getUnumber());
		if (phoneStr.length() != 10) {
			model.addAttribute("error", "Phone number must be exactly 10 digits.");
			return "register";
		}

		// Validate password minimum length
		if (user.getUpassword().trim().length() < 4) {
			model.addAttribute("error", "Password must be at least 4 characters.");
			return "register";
		}

		// Check if email already exists
		User existingUser = this.services.getUserByEmail(email);
		if (existingUser != null) {
			model.addAttribute("error", "An account with this email already exists. Please log in.");
			return "register";
		}

		// Clean inputs and save
		user.setUname(user.getUname().trim());
		user.setUemail(email);
		user.setUpassword(user.getUpassword().trim());

		this.services.addUser(user);
		return "redirect:/login?registered=true";
	}

	@PostMapping("/addingUser")
	public String addUser(@ModelAttribute User user) {
		this.services.addUser(user);
		return "redirect:/admin/services";
	}

	@PostMapping("/updatingUser/{id}")
	public String updateUserPost(@ModelAttribute User user, @PathVariable("id") int id) {
		this.services.updateUser(user, id);
		return "redirect:/admin/services";
	}

	@GetMapping("/updatingUser/{id}")
	public String updateUser(@ModelAttribute User user, @PathVariable("id") int id) {
		this.services.updateUser(user, id);
		return "redirect:/admin/services";
	}

	@GetMapping("/deleteUser/{id}")
	public String deleteUser(@PathVariable("id") int id) {
		this.services.deleteUser(id);
		return "redirect:/admin/services";
	}
}
