package com.telusko.MultProfilesApp.service;

import com.telusko.MultProfilesApp.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();
    boolean addProduct(Product product);
    Product getProductById(Long id);
    boolean updateProductById(Product updatedProduct, Long id);
    boolean deleteProductById(Long id);
}
