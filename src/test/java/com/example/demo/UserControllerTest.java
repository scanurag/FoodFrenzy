package com.example.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.entities.User;
import com.example.demo.services.UserServices;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserServices userServices;

    @Test
    public void testShowRegistrationForm() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("userRegistration"));
    }

    @Test
    public void testShowRegistrationFormHtmlAlias() throws Exception {
        mockMvc.perform(get("/register.html"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    public void testRegisterUserSuccess() throws Exception {
        String uniqueEmail = "testuser_" + System.currentTimeMillis() + "@example.com";

        mockMvc.perform(post("/register")
                .param("uname", "John Doe")
                .param("uemail", uniqueEmail)
                .param("unumber", "9876543210")
                .param("upassword", "password123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?registered=true"));

        User savedUser = userServices.getUserByEmail(uniqueEmail);
        org.junit.jupiter.api.Assertions.assertNotNull(savedUser);
        org.junit.jupiter.api.Assertions.assertEquals("John Doe", savedUser.getUname());
        org.junit.jupiter.api.Assertions.assertEquals(9876543210L, savedUser.getUnumber());
    }

    @Test
    public void testRegisterDuplicateEmail() throws Exception {
        String duplicateEmail = "duplicate_" + System.currentTimeMillis() + "@example.com";
        User existing = new User();
        existing.setUname("Existing User");
        existing.setUemail(duplicateEmail);
        existing.setUnumber(9876543210L);
        existing.setUpassword("pass123");
        userServices.addUser(existing);

        mockMvc.perform(post("/register")
                .param("uname", "Another User")
                .param("uemail", duplicateEmail)
                .param("unumber", "9876543210")
                .param("upassword", "pass123"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("error"));
    }

    @Test
    public void testRegisterInvalidPhone() throws Exception {
        mockMvc.perform(post("/register")
                .param("uname", "Short Phone")
                .param("uemail", "shortphone@example.com")
                .param("unumber", "123")
                .param("upassword", "pass123"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("error"));
    }

    @Test
    public void testRegisterMissingFields() throws Exception {
        mockMvc.perform(post("/register")
                .param("uname", "")
                .param("uemail", "blank@example.com")
                .param("unumber", "9876543210")
                .param("upassword", "pass123"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("error"));
    }
}
