package com.example.demo.repositories;


import org.springframework.data.repository.CrudRepository;

import com.example.demo.entities.Admin;

public interface AdminRepository extends CrudRepository<Admin, Integer>
{
	public Admin findByAdminEmail(String email);
}