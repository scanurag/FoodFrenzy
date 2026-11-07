package com.example.demo.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.example.demo.entities.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {
	Product findByPname(String name);
	Product findByPnameIgnoreCase(String name);
	List<Product> findByPnameContainingIgnoreCase(String name);
}
