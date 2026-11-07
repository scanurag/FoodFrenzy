package com.example.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.entities.Admin;
import com.example.demo.repositories.AdminRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AdminRepository adminRepository;

    @BeforeEach
    public void setupAdmin() {
        if (adminRepository.findByAdminEmail("admin@gmail.com") == null) {
            Admin admin = new Admin();
            admin.setAdminName("Admin");
            admin.setAdminEmail("admin@gmail.com");
            admin.setAdminPassword("admin123");
            admin.setAdminNumber("9876543210");
            adminRepository.save(admin);
        }
    }

    @Test
    public void testAdminLoginSuccess() throws Exception {
        mockMvc.perform(post("/adminLogin")
                .param("email", "admin@gmail.com")
                .param("password", "admin123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/services"));
    }

    @Test
    public void testAdminLoginInvalidCredentials() throws Exception {
        mockMvc.perform(post("/adminLogin")
                .param("email", "admin@gmail.com")
                .param("password", "wrongpassword"))
                .andExpect(status().isOk())
                .andExpect(view().name("Login"))
                .andExpect(model().attributeExists("error"));
    }
}
