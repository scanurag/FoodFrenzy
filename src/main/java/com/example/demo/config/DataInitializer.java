package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.demo.entities.Admin;
import com.example.demo.entities.Product;
import com.example.demo.repositories.AdminRepository;
import com.example.demo.repositories.ProductRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public void run(String... args) throws Exception {
        // Seed default admin if missing
        if (adminRepository.findByAdminEmail("admin@gmail.com") == null) {
            Admin admin = new Admin();
            admin.setAdminName("Admin");
            admin.setAdminEmail("admin@gmail.com");
            admin.setAdminPassword("admin123");
            admin.setAdminNumber("9876543210");
            adminRepository.save(admin);
        }

        // Seed default menu products if they don't exist
        seedProductIfMissing("Chicken Biryani", 410.0, "Delicious Hyderabadi Chicken Biryani");
        seedProductIfMissing("Paneer Butter", 290.0, "Rich creamy Paneer Butter Masala");
        seedProductIfMissing("Afgani Chicken", 410.0, "Creamy Afgani Chicken Curry");
        seedProductIfMissing("Butter Chicken", 380.0, "Classic Delhi Style Butter Chicken");
        seedProductIfMissing("Laccha Paratha", 30.0, "Crispy Layered Laccha Paratha");
        seedProductIfMissing("Matka-Chicken", 510.0, "Special Clay Pot Matka Chicken");
        seedProductIfMissing("Chicken Tikka", 310.0, "Smoky Tandoori Chicken Tikka");
        seedProductIfMissing("Chilli Potato", 150.0, "Crispy Honey Chilli Potato");
        seedProductIfMissing("Chola Bhatura", 180.0, "North Indian Chola Bhatura");
        seedProductIfMissing("Gulab Jamun", 50.0, "Hot Sweet Gulab Jamun");
        seedProductIfMissing("Chowmein", 150.0, "Veg Hakka Chowmein");
        seedProductIfMissing("Paneer Do Pyaza", 270.0, "Delicious Paneer Do Pyaza");
    }

    private void seedProductIfMissing(String name, double price, String description) {
        if (productRepository.findByPname(name) == null && productRepository.findByPnameIgnoreCase(name) == null) {
            Product product = new Product();
            product.setPname(name);
            product.setPprice(price);
            product.setPdescription(description);
            productRepository.save(product);
        }
    }
}
