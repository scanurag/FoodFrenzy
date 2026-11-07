package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entities.Product;
import com.example.demo.services.ProductServices;

@Controller
public class ProductController {

	@Autowired
	private ProductServices productServices;

	// Add Product
	@PostMapping("/addingProduct")
	public String addProduct(@ModelAttribute Product product) {
		this.productServices.addProduct(product);
		return "redirect:/admin/services";
	}

	// Update Product - POST
	@PostMapping("/updatingProduct/{productId}")
	public String updateProductPost(@ModelAttribute Product product, @PathVariable("productId") int id) {
		this.productServices.updateproduct(product, id);
		return "redirect:/admin/services";
	}

	// Update Product - GET (fallback)
	@GetMapping("/updatingProduct/{productId}")
	public String updateProduct(@ModelAttribute Product product, @PathVariable("productId") int id) {
		this.productServices.updateproduct(product, id);
		return "redirect:/admin/services";
	}

	// Delete Product
	@GetMapping("/deleteProduct/{productId}")
	public String delete(@PathVariable("productId") int id) {
		this.productServices.deleteProduct(id);
		return "redirect:/admin/services";
	}
}
