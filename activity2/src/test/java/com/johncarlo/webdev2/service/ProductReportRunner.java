package com.johncarlo.webdev2.service;

import com.johncarlo.webdev2.model.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ProductReportRunner implements CommandLineRunner {

    private final ProductService productService;
    private final String shopName = "My Shop";
    private final String currency = "PHP";

    public ProductReportRunner(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void run(String... args) {
        System.out.println("========================================");
        System.out.println("          PRODUCT REPORT                ");
        System.out.println("========================================");
        System.out.println("Shop: " + shopName);
        System.out.println("Currency: " + currency);
        System.out.println();
        System.out.println("Products above " + currency + " 5000:");

        for (Product p : productService.getProductsAbovePrice(5000)) {
            System.out.println(" - " + p.getName() + ": " + currency + (int) p.getPrice());
        }

        System.out.println("========================================");
    }
}

