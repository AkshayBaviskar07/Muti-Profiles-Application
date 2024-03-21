package com.telusko.MultProfilesApp.service.impl;

import com.telusko.MultProfilesApp.dao.CategoryRepo;
import com.telusko.MultProfilesApp.exceptions.category.CategoryExists;
import com.telusko.MultProfilesApp.exceptions.category.CategoryNotFound;
import com.telusko.MultProfilesApp.model.Category;
import com.telusko.MultProfilesApp.model.Product;
import com.telusko.MultProfilesApp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ProductServiceImpl productService;

    /**
     * Retrieves all categories from the database.
     *
     * @return a list of all categories
     * @throws CategoryNotFound if no categories are found
     */
    @Override
    public List<Category> getAllCategories() {
        // Fetches all categories from database
        List<Category> categories = categoryRepo.findAll();
        // Check if list is empty
        if (categories.isEmpty()) {
            throw new CategoryNotFound("Category not found.");  // Throw an exception if list isEmpty.
        }
        return categories;  // Return list of category.
    }

    /**
     * Adds a new category to the repository if it does not already exist.
     *
     * @param category the category to be added
     * @return true if the category was added, false otherwise
     * @throws CategoryExists if the category name already exists in the repository
     */
    @Override
    public boolean addCategory(Category category) {
        // Check name of category is already exists
        if (categoryRepo.findByName(category.getName()) == null) {
            categoryRepo.save(category);    // Add category in database if name is unique
            return true;    // Return true when record added successfully
        } else {
            // Throw an exception when name already exists.
            throw new CategoryExists("Name already exists! Please try some other name.");
        }
    }

    /**
     * Fetches a category by its ID from database
     *
     * @param id The ID of category to fetch.
     * @return category Object if category was found
     * @throws CategoryNotFound exception if no category found
     */
    @Override
    public Category getCategoryById(Long id) {

        // Find the category by its ID
        Optional<Category> categoryOptional = categoryRepo.findById(id);
        // Record found then return category otherwise throw an exception
        return categoryOptional.orElseThrow(() -> new CategoryNotFound(""));
    }

    /**
     * Updates the category with the given ID by replacing its name and type with the values from the updatedCategory object.
     * If the category with the specified ID is found, it updates the category and returns true.
     * If the category is not found, it throws a CategoryNotFound exception.
     *
     * @param updatedCategory The Category object containing the updated name and type.
     * @param id The ID of the category to update.
     * @return true if the category is updated successfully.
     * @throws CategoryNotFound if the category with the specified ID is not found.
     */
    @Override
    public boolean updateCategory(Category updatedCategory, Long id) {
        // Find the category by its ID
        Optional<Category> categoryOptional = categoryRepo.findById(id);
        // Check if category is present
        if(categoryOptional.isPresent()){

            // Update the category
            Category category = categoryOptional.get();
            category.setName(updatedCategory.getName());
            category.setType(updatedCategory.getType());

            categoryRepo.save(category);    // Save updated category to database
            return true;    // Return true if category was updated.
        } else {
            //throws an exception if category not found
            throw new CategoryNotFound("Category not found.");
        }
    }

    /**
     * Deletes a category by its ID.
     *
     * @param id The ID of the category to delete.
     * @return True if the category was successfully deleted, false otherwise.
     * @throws CategoryNotFound If the category with the given ID does not exist.
     */
    @Override
    public boolean deleteCategoryById(Long id) {
        // Find the category by its ID
        Optional<Category> categoryOptional = categoryRepo.findById(id);
        // Check if category is present
        if (categoryOptional.isPresent()) {
            // Deletes the category
            categoryRepo.deleteById(id);
            return true;    // Return true if category was successfully deleted
        } else {
            // If the category with the given ID does not exist.
            throw new CategoryNotFound("Category not found.");
        }
    }

    /**
     * Adds the products into specific category
     * @param catId The category ID used to fetch category
     * @param prodId The product ID used to fetch product
     * @return true if product successfully add into category otherwise false
     */
    @Override
    public boolean addProductInCategory(Long catId, Long prodId) {
        // Get the product by ID
        Product product = productService.getProductById(prodId);
        // Get the category by ID
        Category category = getCategoryById(catId);
        // Check if category is present or not
        if(category != null) {
            category.getProducts().add(product);    // add the product into category
            product.setCategory(category);      // add the category into product
            // Update the changes
            categoryRepo.save(category);
            productService.updateProductById(product, prodId);

            // Return true if product successfully added into category
            return true;
        } else {
            // Return false if product not added
            return false;
        }
    }

    /**
     * Removes the products from specific category
     * @param catId The category ID used to fetch category
     * @param prodId The product ID used to fetch product
     * @return true if product successfully removed from category otherwise false
     */
    @Override
    public boolean removeProductFromCategory(Long prodId, Long catId) {
        // Get the product by ID
        Product product = productService.getProductById(prodId);
        // Get the category by ID
        Category category = getCategoryById(catId);
        // Check if category is present or not
        if(category != null) {
            category.getProducts().remove(product); // Remove product from category
            categoryRepo.save(category);       // Update the category
            product.setCategory(null);         // Remove category from product
            productService.updateProductById(product,prodId);   // Update the product

            // Return true if product removed successfully
            return true;
        } else {
            // Return true if product not removed
            return false;
        }
    }


}
