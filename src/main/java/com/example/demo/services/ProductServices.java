package com.example.demo.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Product;
import com.example.demo.repositories.ProductRepository;

@Service
public class ProductServices {

	@Autowired
	private ProductRepository productRepository;

	public void addProduct(Product p) {
		this.productRepository.save(p);
	}

	public List<Product> getAllProducts() {
		return (List<Product>) this.productRepository.findAll();
	}

	public Product getProduct(int id) {
		return this.productRepository.findById(id).orElse(null);
	}

	public void updateproduct(Product p, int id) {
		Product prod = this.productRepository.findById(id).orElse(null);
		if (prod != null) {
			prod.setPname(p.getPname());
			prod.setPprice(p.getPprice());
			prod.setPdescription(p.getPdescription());
			this.productRepository.save(prod);
		}
	}

	public void deleteProduct(int id) {
		this.productRepository.deleteById(id);
	}

	public Product getProductByName(String name) {
		if (name == null || name.trim().isEmpty()) {
			return null;
		}
		String trimmed = name.trim();
		Product product = this.productRepository.findByPname(trimmed);
		if (product != null) {
			return product;
		}
		product = this.productRepository.findByPnameIgnoreCase(trimmed);
		if (product != null) {
			return product;
		}
		List<Product> list = this.productRepository.findByPnameContainingIgnoreCase(trimmed);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
}
