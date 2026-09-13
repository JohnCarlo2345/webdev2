package com.johncarlo.webdev2.model;

import com.johncarlo.webdev2.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {
    private final List<Product> products = new ArrayList<>();

    public ProductRepository() {
        products.add(new Product("Laptop", 45000));
        products.add(new Product("Keyboard", 800));
        products.add(new Product("Monitor", 12000));
        products.add(new Product("Mouse", 350));
        products.add(new Product("Printer", 8500));
    }

    public List<Product> findAll() {
        return products;
    }
}

