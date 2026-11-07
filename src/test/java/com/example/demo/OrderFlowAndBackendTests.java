package com.example.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.entities.Admin;
import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.repositories.AdminRepository;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.AdminServices;
import com.example.demo.services.ProductServices;
import com.example.demo.services.UserServices;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderFlowAndBackendTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserServices userServices;

    @Autowired
    private AdminServices adminServices;

    @Autowired
    private ProductServices productServices;

    private User testUser;
    private Product testProduct;

    @BeforeEach
    public void setupData() {
        String testEmail = "buyer@example.com";
        testUser = userRepository.findUserByUemail(testEmail);
        if (testUser == null) {
            testUser = new User();
            testUser.setUemail(testEmail);
            testUser.setUpassword("password123");
            testUser.setUnumber(9876543210L);
        }
        testUser.setUname("Buyer User");
        testUser.setUpassword("password123");
        testUser = userRepository.save(testUser);

        testProduct = productRepository.findByPnameIgnoreCase("Chicken Biryani");
        if (testProduct == null) {
            testProduct = new Product();
            testProduct.setPname("Chicken Biryani");
            testProduct.setPprice(410.0);
            testProduct.setPdescription("Delicious Hyderabadi Chicken Biryani");
            testProduct = productRepository.save(testProduct);
        }
    }

    @Test
    public void testBuyNowWhenLoggedInNavigatesDirectlyToBuyProduct() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", testUser);

        mockMvc.perform(get("/product/buy").param("name", "Chicken Biryani").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("BuyProduct"))
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attributeExists("orders"))
                .andExpect(model().attribute("name", "Buyer User"));
    }

    @Test
    public void testBuyNowWhenNotLoggedInRedirectsToLoginAndRemembersProduct() throws Exception {
        MockHttpSession session = new MockHttpSession();

        mockMvc.perform(get("/product/buy").param("name", "Chicken Biryani").session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?buyRequired=true"));

        Assertions.assertEquals("Chicken Biryani", session.getAttribute("pendingProduct"));
    }

    @Test
    public void testLoginAfterBuyNowRedirectsToSelectedProduct() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("pendingProduct", "Chicken Biryani");

        mockMvc.perform(post("/userLogin")
                .param("userEmail", "buyer@example.com")
                .param("userPassword", "password123")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/buy?name=Chicken+Biryani"));

        Assertions.assertNull(session.getAttribute("pendingProduct"));
        Assertions.assertNotNull(session.getAttribute("user"));
    }

    @Test
    public void testNormalLoginRedirectsToBuyProduct() throws Exception {
        MockHttpSession session = new MockHttpSession();

        mockMvc.perform(post("/userLogin")
                .param("userEmail", "buyer@example.com")
                .param("userPassword", "password123")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/buyProduct"));

        Assertions.assertNotNull(session.getAttribute("user"));
    }

    @Test
    public void testPlaceOrderSuccess() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", testUser);

        mockMvc.perform(post("/product/order")
                .param("oName", "Chicken Biryani")
                .param("oPrice", "410.0")
                .param("oQuantity", "2")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("Order_success"))
                .andExpect(model().attribute("amount", 820.0));
    }

    @Test
    public void testPlaceOrderWithoutLoginRedirectsToLogin() throws Exception {
        mockMvc.perform(post("/product/order")
                .param("oName", "Chicken Biryani")
                .param("oPrice", "410.0")
                .param("oQuantity", "2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    public void testSearchProductCaseInsensitive() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", testUser);

        mockMvc.perform(post("/product/search")
                .param("productName", "chicken biryani")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("BuyProduct"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    public void testLogoutClearsSession() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", testUser);

        mockMvc.perform(get("/logout").session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        Assertions.assertTrue(session.isInvalid());
    }

    @Test
    public void testUpdateAdminPreservesPassword() throws Exception {
        Admin admin = adminRepository.findByAdminEmail("admin@gmail.com");
        Assertions.assertNotNull(admin);
        String originalPassword = admin.getAdminPassword();

        Admin updatePayload = new Admin();
        updatePayload.setAdminName("Super Admin");
        updatePayload.setAdminEmail("admin@gmail.com");
        updatePayload.setAdminNumber("9998887776");

        adminServices.update(updatePayload, admin.getAdminId());

        Admin updated = adminServices.getAdmin(admin.getAdminId());
        Assertions.assertEquals("Super Admin", updated.getAdminName());
        Assertions.assertEquals(originalPassword, updated.getAdminPassword());
    }

    @Test
    public void testUpdateUserPreservesPassword() throws Exception {
        User user = userRepository.findUserByUemail("buyer@example.com");
        Assertions.assertNotNull(user);
        String originalPassword = user.getUpassword();

        User updatePayload = new User();
        updatePayload.setUname("Updated Buyer");
        updatePayload.setUemail("buyer@example.com");
        updatePayload.setUnumber(9123456789L);

        userServices.updateUser(updatePayload, user.getU_id());

        User updated = userServices.getUser(user.getU_id());
        Assertions.assertEquals("Updated Buyer", updated.getUname());
        Assertions.assertEquals(originalPassword, updated.getUpassword());
    }
}
