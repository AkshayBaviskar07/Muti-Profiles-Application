package com.telusko.MultProfilesApp.service.impl;

import com.telusko.MultProfilesApp.dao.ProductRepo;
import com.telusko.MultProfilesApp.exceptions.category.CategoryNotFound;
import com.telusko.MultProfilesApp.exceptions.company.CompanyNotFound;
import com.telusko.MultProfilesApp.exceptions.product.ProductNotFound;
import com.telusko.MultProfilesApp.model.Category;
import com.telusko.MultProfilesApp.model.Company;
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
    @Autowired
    private CompanyServiceImpl companyService;
    @Autowired
    private CategoryServiceImpl categoryService;


    /**
     * Retrieves all products for a given company and category.
     *
     * @param companyId the ID of the company
     * @param categoryId the ID of the category
     * @return a list of products
     * @throws CompanyNotFound if the company is not found
     * @throws CategoryNotFound if the category is not found
     * @throws ProductNotFound if no products are found for the category
     */
    @Override
    public List<Product> getAllProducts(Long companyId, Long categoryId) {
        // Retrieve the company by ID
        Company company = companyService.getCompanyById(companyId);

        // Check if the company exists
        if(company != null) {
            // Check if the category exists within the company
            Optional<Category> availableCat = company.getCategories()
                    .stream()
                    .filter(category -> category.getId().equals(categoryId))
                    .findFirst();

            // If the category exists, retrieve and return its products
            if (availableCat.isPresent()) {
                List<Product> products = availableCat.get().getProducts();
                // Check if the products list is empty
                if(!products.isEmpty()) {
                    return products;
                } else {
                    // Throw an exception if no products are found for the category
                    throw new ProductNotFound("Products Not Found");
                }
            } else {
                // Throw an exception if the category is not found
                throw new CategoryNotFound("Category Not Found");
            }
        } else {
            // Throw an exception if the company is not found
            throw new CompanyNotFound("Company Not Found");
        }
    }

    /**
     * Adds a product to a category in a company.
     *
     * @param  companyId the ID of the company
     * @param  categoryId the ID of the category
     * @param  product the product to be added
     * @return true if the product was added successfully, false otherwise
     */
    @Override
    public boolean addProduct(Long companyId, Long categoryId,Product product) {
        // Get the company by ID
        Company company = companyService.getCompanyById(companyId);

        // Check if company exists
        if(company != null) {
            // Find the category by ID within the company
            Optional<Category> availableCat = company.getCategories()
                    .stream()
                    .filter(category -> category.getId().equals(categoryId))
                    .findFirst();

            // Check if the category exists
            if (availableCat.isPresent()) {
                // Get the available category
                Category availableCategory = availableCat.get();
                // Add product in category's product list
                availableCategory.getProducts().add(product);
                // Set the product's category to the available category
                product.setCategory(availableCategory);
                // Save the product in the repository
                productRepo.save(product);
                // Update the category in the category service
                return categoryService.updateCategory(companyId, availableCategory, categoryId);
            } else {
                throw new CategoryNotFound("Category Not Found");
            }
        } else {
            throw new CompanyNotFound("Company Not Found");
        }
    }

    /**
     * Get a product by its ID.
     *
     * @param id the ID of the product
     * @return the product with the given ID, or null if not found
     */
    @Override
    public Product getProductById(Long companyId, Long categoryId, Long productId) {
        Company company = companyService.getCompanyById(companyId);
        if (company != null) {
            Optional<Category> availableCat = company.getCategories()
                    .stream()
                    .filter(category -> category.getId().equals(categoryId))
                    .findFirst();

            if(availableCat.isPresent()) {
                Category category = availableCat.get();

                Optional<Product> productOptional = category.getProducts()
                        .stream().filter(product -> product.getCode().equals(productId))
                        .findFirst();

                if(productOptional.isPresent()) {
                    return productOptional.get();
                } else {
                    throw new ProductNotFound("Product Not Found");
                }
            } else {
                throw new CategoryNotFound("Category Not Found");
            }
        } else {
            throw new CompanyNotFound("Company Not Found");
        }
    }

    /**
     * Updates the product with the given ID in the database.
     *
     * @param companyId the ID of the company
     * @param categoryId the ID of the category
     * @param updatedProduct the updated product object
     * @param productId the ID of the product to be updated
     * @return true if the product was successfully updated, false otherwise
     */
    @Override
    public boolean updateProductById(Long companyId,
                                     Long categoryId,
                                     Product updatedProduct,
                                     Long productId) {

        // Retrieve the company object from the database
        Company company = companyService.getCompanyById(companyId);

        // Check if the company exists
        if (company != null) {
            // Find the category within the company
            Optional<Category> categoryOptional = company.getCategories()
                    .stream().filter(category -> category.getId().equals(categoryId))
                    .findFirst();

            // Check if the category exists
            if(categoryOptional.isPresent()) {
                // Find the product within the category
                Optional<Product> productOptional = categoryOptional.get()
                        .getProducts()
                        .stream()
                        .filter(product -> product.getCode().equals(productId))
                        .findFirst();

                // Check if the product exists
                if (productOptional.isPresent()) {
                    // Update the product details
                    Product product = productOptional.get();
                    product.setName(updatedProduct.getName());
                    product.setPrice(updatedProduct.getPrice());
                    product.setMfgDate(updatedProduct.getMfgDate());
                    product.setExpiryDate(updatedProduct.getExpiryDate());

                    // Save the updated product to the database
                    productRepo.save(product);
                    return true;
                } else {
                    // Throw exception if product is not found
                    throw new ProductNotFound("Product Not Found");
                }
            } else {
                // Throw exception if category is not found
                throw new CategoryNotFound("Category Not Found");
            }
        } else {
            // Throw exception if company is not found
            throw new CompanyNotFound("Company Not Found");
        }
    }

    /**
     * Delete a product by its ID from a specific category of a company
     *
     * @param companyId The ID of the company
     * @param categoryId The ID of the category
     * @param productId The ID of the product to be deleted
     * @return true if product deleted successfully otherwise false
     * @throws CompanyNotFound if the company not found with specified ID
     * @throws CategoryNotFound if the category not found with specified ID
     * @throws ProductNotFound if the product not found with specified ID
     */
    @Override
    public boolean deleteProductById(Long companyId, Long categoryId, Long productId) {
        // Get the company by ID
        Company company = companyService.getCompanyById(companyId);
        if (company != null) {
            // Find the category by ID within company
            Optional<Category> categoryOptional = company.getCategories()
                    .stream()
                    .filter(category -> category.getId().equals(categoryId))
                    .findFirst();

            if (categoryOptional.isPresent()) {
                // Get the category
                Category category = categoryOptional.get();
                // Find the product by ID within product
                Optional<Product> productOptional = category.getProducts()
                        .stream()
                        .filter(product -> product.getCode().equals(productId))
                        .findFirst();

                if(productOptional.isPresent()) {
                    // Get the product
                    Product product = productOptional.get();

                    // Remove product from category
                    category.getProducts().remove(product);

                    // Delete product from database
                    productRepo.deleteById(productId);
                    // Update category
                    categoryService.updateCategory(companyId, category, categoryId);
                    return true;
                } else {
                    throw new ProductNotFound("Product Not Found");
                }
            } else {
                throw new CategoryNotFound("Category Not Found");
            }
        } else {
            throw new CompanyNotFound("Company Not Found");
        }
    }
}
