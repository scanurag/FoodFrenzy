package com.example.demo.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.count.Logic;
import com.example.demo.entities.Orders;
import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.services.OrderServices;
import com.example.demo.services.ProductServices;

import jakarta.servlet.http.HttpSession;

@Controller
public class OrderController {

	@Autowired
	private ProductServices productServices;

	@Autowired
	private OrderServices orderServices;

	@GetMapping("/buyProduct")
	public String buyProductPage(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}
		List<Orders> orders = this.orderServices.getOrdersForUser(user);
		model.addAttribute("orders", orders);
		model.addAttribute("name", user.getUname());
		return "BuyProduct";
	}

	@GetMapping("/product/buy")
	public String buyProductDirect(@RequestParam(value = "name", required = false) String name,
								   @RequestParam(value = "id", required = false) Integer id,
								   HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			// User not logged in, remember what product they wanted to order
			if (name != null && !name.trim().isEmpty()) {
				session.setAttribute("pendingProduct", name.trim());
			} else if (id != null) {
				Product p = this.productServices.getProduct(id);
				if (p != null) {
					session.setAttribute("pendingProduct", p.getPname());
				}
			}
			return "redirect:/login?buyRequired=true";
		}

		// User IS logged in: load the product directly on the buy page
		Product product = null;
		if (id != null) {
			product = this.productServices.getProduct(id);
		} else if (name != null && !name.trim().isEmpty()) {
			product = this.productServices.getProductByName(name.trim());
		}

		if (product == null) {
			model.addAttribute("message", "SORRY...! Product Unavailable");
		} else {
			model.addAttribute("product", product);
		}

		List<Orders> orders = this.orderServices.getOrdersForUser(user);
		model.addAttribute("orders", orders);
		model.addAttribute("name", user.getUname());
		return "BuyProduct";
	}

	@PostMapping("/product/search")
	public String searchHandler(@RequestParam("productName") String name, HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}

		Product product = this.productServices.getProductByName(name);
		if (product == null) {
			model.addAttribute("message", "SORRY...! Product Unavailable");
		} else {
			model.addAttribute("product", product);
		}

		List<Orders> orders = this.orderServices.getOrdersForUser(user);
		model.addAttribute("orders", orders);
		model.addAttribute("name", user.getUname());
		return "BuyProduct";
	}

	@PostMapping("/product/order")
	public String orderHandler(@ModelAttribute() Orders order, HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}

		if (order.getoQuantity() <= 0) {
			order.setoQuantity(1);
		}

		double totalAmount = Logic.countTotal(order.getoPrice(), order.getoQuantity());
		order.setTotalAmmout(totalAmount);
		order.setUser(user);
		order.setOrderDate(new Date());

		this.orderServices.saveOrder(order);
		model.addAttribute("amount", totalAmount);
		return "Order_success";
	}

	@GetMapping("/product/back")
	public String back(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}
		return "redirect:/buyProduct";
	}
}
