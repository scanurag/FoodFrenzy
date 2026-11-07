package com.example.demo.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;

@Service
public class UserServices {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<User> getAllUser() {
		return (List<User>) this.userRepository.findAll();
	}

	public User getUser(int id) {
		return this.userRepository.findById(id).orElse(null);
	}

	public User getUserByEmail(String email) {
		if (email == null) {
			return null;
		}
		return this.userRepository.findUserByUemail(email.trim());
	}

	public void updateUser(User user, int id) {
		User existing = this.userRepository.findById(id).orElse(null);
		if (existing != null) {
			existing.setUname(user.getUname());
			existing.setUemail(user.getUemail());
			existing.setUnumber(user.getUnumber());
			if (user.getUpassword() != null && !user.getUpassword().trim().isEmpty()) {
				existing.setUpassword(user.getUpassword());
			}
			this.userRepository.save(existing);
		}
	}

	public void deleteUser(int id) {
		this.userRepository.deleteById(id);
	}

	public void addUser(User user) {
		this.userRepository.save(user);
	}

	public boolean validateLoginCredentials(String email, String password) {
		if (email == null || password == null) {
			return false;
		}
		User u = this.userRepository.findUserByUemail(email.trim());
		if (u != null && u.getUpassword() != null) {
			return u.getUpassword().equals(password) 
					|| passwordEncoder.matches(password, u.getUpassword());
		}
		return false;
	}
}
