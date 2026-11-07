package com.example.demo.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Admin;
import com.example.demo.repositories.AdminRepository;

@Service
public class AdminServices {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<Admin> getAll() {
		return (List<Admin>) this.adminRepository.findAll();
	}

	public Admin getAdmin(int id) {
		return this.adminRepository.findById(id).orElse(null);
	}

	public void update(Admin admin, int id) {
		Admin existing = this.adminRepository.findById(id).orElse(null);
		if (existing != null) {
			existing.setAdminName(admin.getAdminName());
			existing.setAdminEmail(admin.getAdminEmail());
			existing.setAdminNumber(admin.getAdminNumber());
			if (admin.getAdminPassword() != null && !admin.getAdminPassword().trim().isEmpty()) {
				existing.setAdminPassword(admin.getAdminPassword());
			}
			this.adminRepository.save(existing);
		}
	}

	public void delete(int id) {
		this.adminRepository.deleteById(id);
	}

	public void addAdmin(Admin admin) {
		this.adminRepository.save(admin);
	}

	public boolean validateAdminCredentials(String email, String password) {
		if (email == null || password == null) {
			return false;
		}
		Admin admin = adminRepository.findByAdminEmail(email.trim());
		if (admin != null && admin.getAdminPassword() != null) {
			return admin.getAdminPassword().equals(password) 
					|| passwordEncoder.matches(password, admin.getAdminPassword());
		}
		return false;
	}
}
