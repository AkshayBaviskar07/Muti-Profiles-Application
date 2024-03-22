package com.telusko.MultProfilesApp.controller;

import com.telusko.MultProfilesApp.model.Product;
import com.telusko.MultProfilesApp.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company/{companyId}/category/{categoryId}/product")
public class ProductController {
    @Autowired
    private ProductServiceImpl productService;

    /**
     * Retrieves all products for a given company and category.
     * @param companyId the ID of the company
     * @param categoryId the ID of the category
     * @return ResponseEntity with a list of products if found, or NOT_FOUND if the list is empty
     */
    @GetMapping
    private ResponseEntity<List<Product>> getAllProducts(@PathVariable Long companyId,
                                                         @PathVariable Long categoryId) {
        // Retrieve all products for the given company and category
        List<Product> products = productService.getAllProducts(companyId, categoryId);

        // Check if the list of products is empty
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Return the list of products with OK status
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * A description of the entire Java function.
     *
     * @param companyId description of parameter
     * @param categoryId description of parameter
     * @param product description of parameter
     * @return description of return value
     */
    @PostMapping
    private ResponseEntity<String> addProduct (@PathVariable Long companyId,
                                               @PathVariable Long categoryId,
                                               @RequestBody Product product) {
        boolean isAdded = productService.addProduct(companyId, categoryId ,product);
        if (isAdded) {
            return new ResponseEntity<>("Product successfully added", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * A description of the entire Java function.
     *
     * @param companyId description of parameter
     * @param categoryId description of parameter
     * @param productId	description of parameter
     * @return description of return value
     */
    @GetMapping("/{productId}")
    private ResponseEntity<Product> getProductById(@PathVariable Long companyId,
                                                   @PathVariable Long categoryId,
                                                   @PathVariable Long productId) {

        Product product = productService.getProductById(companyId, categoryId, productId);
        if(product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Update a product by its ID.
     *
     * @param companyId the company ID
     * @param categoryId the category ID
     * @param updatedProduct the updated product
     * @param productId the product ID
     * @return a ResponseEntity with a success message if the product was updated, otherwise NOT_FOUND
     */
    @PutMapping("/{productId}")
    private ResponseEntity<String> updateProductById(@PathVariable Long companyId,
                                                     @PathVariable Long categoryId,
                                                     @RequestBody Product updatedProduct,
                                                     @PathVariable Long productId) {
        boolean isUpdated = productService.updateProductById(companyId, categoryId, updatedProduct, productId);
        if (isUpdated) {
            return new ResponseEntity<>("Product updated successfully!", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Delete a product by its ID.
     *
     * @param companyId the ID of the company
     * @param categoryId the ID of the category
     * @param productId the ID of the product
     * @return a ResponseEntity with a success or not found message
     */
    @DeleteMapping("/{productId}")
    private ResponseEntity<String> deleteProductById(@PathVariable Long companyId,
                                                     @PathVariable Long categoryId,
                                                     @PathVariable Long productId) {
        boolean isDeleted = productService.deleteProductById(companyId, categoryId, productId);
        if (isDeleted) {
            return new ResponseEntity<>("Product deleted successfully!", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
