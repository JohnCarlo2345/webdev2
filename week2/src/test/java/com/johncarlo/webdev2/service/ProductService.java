package com.johncarlo.webdev2.service;

import com.johncarlo.webdev2.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    public interface ProductRepository {
        List<Product> findAll();
    }

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsAbovePrice(double threshold) {
        return productRepository.findAll().stream()
                .filter(p -> p.getPrice() > threshold)
                .collect(Collectors.toList());
    }
}


