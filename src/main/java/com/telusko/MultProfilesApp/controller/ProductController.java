package com.telusko.MultProfilesApp.controller;

import com.telusko.MultProfilesApp.model.Product;
import com.telusko.MultProfilesApp.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductServiceImpl productService;

    /**
     * Get all products
     *
     * @return ResponseEntity with a list of products or NOT_FOUND if the list is empty
     */
    @GetMapping
    private ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * Adds a new product to the system.
     *
     * @param product The product to be added.
     * @return A ResponseEntity containing the result of the operation.
     */
    @PostMapping
    private ResponseEntity<String> addProduct (@RequestBody Product product) {
        boolean isAdded = productService.addProduct(product);
        if (isAdded) {
            return new ResponseEntity<>("Product successfully added", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Get a product by its ID.
     *
     * @param id The ID of the product to retrieve.
     * @return ResponseEntity containing the product if found, otherwise return NOT FOUND status.
     */
    @GetMapping("/{id}")
    private ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if(product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Update a product by its ID.
     *
     * @param product The updated product information.
     * @param id The ID of the product to update.
     * @return ResponseEntity with a success message if the product was updated successfully,
     *         or a NOT_FOUND status if the product was not found.
     */
    @PutMapping("/{id}")
    private ResponseEntity<String> updateProductById(@RequestBody Product product,
                                                     @PathVariable Long id) {
        boolean isUpdated = productService.updateProductById(product, id);
        if (isUpdated) {
            return new ResponseEntity<>("Product updated successfully!", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Delete a product by its ID
     *
     * @param id the ID of the product to delete
     * @return a ResponseEntity with a message indicating success or failure
     */
    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteProductById(@PathVariable Long id) {
        boolean isDeleted = productService.deleteProductById(id);
        if (isDeleted) {
            return new ResponseEntity<>("Product deleted successfully!", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
