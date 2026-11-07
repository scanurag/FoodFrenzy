package com.example.demo.controllers;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entities.Admin;
import com.example.demo.entities.Orders;
import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.loginCredentials.AdminLogin;
import com.example.demo.loginCredentials.UserLogin;
import com.example.demo.services.AdminServices;
import com.example.demo.services.OrderServices;
import com.example.demo.services.ProductServices;
import com.example.demo.services.UserServices;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

	@Autowired
	private UserServices services;

	@Autowired
	private AdminServices adminServices;

	@Autowired
	private ProductServices productServices;

	@Autowired
	private OrderServices orderServices;

	@PostMapping("/adminLogin")
	public String getAllData(@ModelAttribute("adminLogin") AdminLogin login, HttpSession session, Model model) {
		String email = login.getEmail();
		String password = login.getPassword();
		if (adminServices.validateAdminCredentials(email, password)) {
			Admin admin = adminServices.getAll().stream()
					.filter(a -> a.getAdminEmail() != null && a.getAdminEmail().equalsIgnoreCase(email))
					.findFirst().orElse(null);
			session.setAttribute("admin", admin);
			return "redirect:/admin/services";
		} else {
			model.addAttribute("error", "Invalid email or password");
			return "Login";
		}
	}

	@PostMapping("/userLogin")
	public String userLogin(@ModelAttribute("userLogin") UserLogin login, HttpSession session, Model model) {
		String email = login.getUserEmail();
		String password = login.getUserPassword();
		if (services.validateLoginCredentials(email, password)) {
			User user = this.services.getUserByEmail(email);
			session.setAttribute("user", user);

			// Check if user was trying to buy a product before login
			String pendingProduct = (String) session.getAttribute("pendingProduct");
			if (pendingProduct != null && !pendingProduct.trim().isEmpty()) {
				session.removeAttribute("pendingProduct");
				return "redirect:/product/buy?name=" + URLEncoder.encode(pendingProduct, StandardCharsets.UTF_8);
			}

			return "redirect:/buyProduct";
		} else {
			model.addAttribute("error2", "Invalid email or password");
			return "Login";
		}
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		if (session != null) {
			session.invalidate();
		}
		return "redirect:/login";
	}

	@GetMapping("/admin/services")
	public String returnBack(HttpSession session, Model model) {
		List<User> users = this.services.getAllUser();
		List<Admin> admins = this.adminServices.getAll();
		List<Product> products = this.productServices.getAllProducts();
		List<Orders> orders = this.orderServices.getOrders();
		model.addAttribute("users", users);
		model.addAttribute("admins", admins);
		model.addAttribute("products", products);
		model.addAttribute("orders", orders);

		return "Admin_Page";
	}

	@GetMapping("/addAdmin")
	public String addAdminPage() {
		return "Add_Admin";
	}

	@PostMapping("/addingAdmin")
	public String addAdmin(@ModelAttribute Admin admin) {
		this.adminServices.addAdmin(admin);
		return "redirect:/admin/services";
	}

	@GetMapping("/updateAdmin/{adminId}")
	public String update(@PathVariable("adminId") int id, Model model) {
		Admin admin = this.adminServices.getAdmin(id);
		model.addAttribute("admin", admin);
		return "Update_Admin";
	}

	@PostMapping("/updatingAdmin/{id}")
	public String updateAdminPost(@ModelAttribute Admin admin, @PathVariable("id") int id) {
		this.adminServices.update(admin, id);
		return "redirect:/admin/services";
	}

	@GetMapping("/updatingAdmin/{id}")
	public String updateAdminGet(@ModelAttribute Admin admin, @PathVariable("id") int id) {
		this.adminServices.update(admin, id);
		return "redirect:/admin/services";
	}

	@GetMapping("/deleteAdmin/{id}")
	public String deleteAdmin(@PathVariable("id") int id) {
		this.adminServices.delete(id);
		return "redirect:/admin/services";
	}

	@GetMapping("/addProduct")
	public String addProduct() {
		return "Add_Product";
	}

	@GetMapping("/updateProduct/{productId}")
	public String updateProduct(@PathVariable("productId") int id, Model model) {
		Product product = this.productServices.getProduct(id);
		model.addAttribute("product", product);
		return "Update_Product";
	}

	@GetMapping("/addUser")
	public String addUser() {
		return "Add_User";
	}

	@GetMapping("/updateUser/{userId}")
	public String updateUserPage(@PathVariable("userId") int id, Model model) {
		User user = this.services.getUser(id);
		model.addAttribute("user", user);
		return "Update_User";
	}
}
