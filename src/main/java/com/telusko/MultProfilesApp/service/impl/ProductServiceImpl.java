package com.telusko.MultProfilesApp.service.impl;

import com.telusko.MultProfilesApp.dao.ProductRepo;
import com.telusko.MultProfilesApp.model.Product;
import com.telusko.MultProfilesApp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepo productRepo;

    /**
     * Retrieve all products from the repository
     * @return List of all products
     */
    @Override
    public List<Product> getAllProducts() {
        List<Product> products = productRepo.findAll();
        if (products.isEmpty()) {
            return new ArrayList<>();
        }
        return products;
    }

    /**
     * Adds a product to the repository if it doesn't already exist.
     *
     * @param product the product to be added
     * @return true if the product was added successfully, false otherwise
     */
    @Override
    public boolean addProduct(Product product) {
        // Check if a product with the same name already exists in the repository
        if (productRepo.findByName(product.getName()) == null) {
            // If not, save the new product to the repository and return true
            productRepo.save(product);
            return true;
        }
        // If a product with the same name already exists, return false
        return false;
    }

    /**
     * Get a product by its ID.
     *
     * @param id the ID of the product
     * @return the product with the given ID, or null if not found
     */
    @Override
    public Product getProductById(Long id) {
        // Find product by ID in the repository
        Optional<Product> productOptional = productRepo.findById(id);
        // Return the product if found, otherwise return null
        return productOptional.orElse(null);
    }

    /**
     * Updates the product with the given ID in the database.
     *
     * @param updatedProduct the updated product object
     * @param id the ID of the product to be updated
     * @return true if the product was successfully updated, false otherwise
     */
    @Override
    public boolean updateProductById(Product updatedProduct, Long id) {
        // Check if the product with the given ID exists in the database
        Optional<Product> productOptional = productRepo.findById(id);
        if (productOptional.isPresent()) {
            // Get the existing product
            Product product = productOptional.get();
            // Update the product
            product.setName(product.getName());
            product.setPrice(product.getPrice());
            product.setMfgDate(product.getMfgDate());
            product.setExpiryDate(product.getExpiryDate());

            // Save the updated product in the database
            productRepo.save(product);
            return true;
        }
        return false;
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id The ID of the product to delete.
     * @return True if the product was successfully deleted, false otherwise.
     */
    @Override
    public boolean deleteProductById(Long id) {
        // Check if the product exists
        Optional<Product> productOptional = productRepo.findById(id);
        // Delete the product if it exists
        if (productOptional.isPresent()) {
            productRepo.deleteById(id);
            return true;    // Return true if product was successfully deleted
        }
        return false;   // Return true if product not found
    }


}
