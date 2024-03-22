package com.telusko.MultProfilesApp.service;

import com.telusko.MultProfilesApp.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts(Long companyId, Long categoryId);
    boolean addProduct(Long companyId, Long categoryId, Product product);
    Product getProductById(Long companyId, Long categoryId, Long productId);
    boolean updateProductById(Long companyId, Long categoryId, Product updatedProduct, Long productId);
    boolean deleteProductById(Long companyId, Long categoryId, Long productId);
}
