package com.example.demo.repositories;


import org.springframework.data.repository.CrudRepository;

import com.example.demo.entities.Product;

public interface ProductRepository extends CrudRepository<Product,Integer>
{
	public Product findByPname(String name);

}