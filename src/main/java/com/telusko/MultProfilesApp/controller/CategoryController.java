package com.telusko.MultProfilesApp.controller;

import com.telusko.MultProfilesApp.model.Category;
import com.telusko.MultProfilesApp.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company/{companyId}/category")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    /**
     * Retrieves all categories for a given company.
     * @param companyId the ID of the company to retrieve categories for
     * @return ResponseEntity with a list of categories and HTTP status
     */
    @GetMapping
    private ResponseEntity<List<Category>> getAllCategories(@PathVariable Long companyId) {
        List<Category> categories = categoryService.getAllCategories(companyId);
        if (!categories.isEmpty()) {
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint to add a new category.
     * @param category the category to be added
     * @param companyId the ID of the company to associate the category with
     * @return ResponseEntity with a success message if the category was added successfully, or an error status otherwise
     */
    @PostMapping
    private ResponseEntity<String> addCategory(@RequestBody Category category,
                                               @PathVariable Long companyId) {
        // Call the categoryService to add the category with the given companyId
        boolean isAdded = categoryService.addCategory(category, companyId);

        // Check if the category was added successfully and return the appropriate response
        if (isAdded) {
            return new ResponseEntity<>("Category added successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get category by ID
     * @param companyId The ID of the company
     * @param catId The ID of the category
     * @return ResponseEntity with the category if found, otherwise NOT_FOUND
     */
    @GetMapping("/{catId}")
    private ResponseEntity<Category> getCategoryById(@PathVariable Long companyId,
                                                     @PathVariable Long catId) {
        // Retrieve the category from the service
        Category category = categoryService.getCategoryById(companyId, catId);

        // Check if the category exists
        if(category != null) {
            // Return the category with HttpStatus OK if found
            return new ResponseEntity<>(category, HttpStatus.OK);
        } else {
            // Return NOT_FOUND if category not found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Updates a category by its ID.
     * @param companyId The ID of the company.
     * @param category The updated category object.
     * @param catId The ID of the category to update.
     * @return ResponseEntity with a success message if the category was updated successfully,
     *         or NOT_FOUND status if the category was not found.
     */
    @PutMapping("/{catId}")
    private ResponseEntity<String> updateCategoryById(@PathVariable Long companyId,
                                                      @RequestBody Category category,
                                                      @PathVariable Long catId) {

        // Call the categoryService to update the category
        boolean isUpdated = categoryService.updateCategory(companyId, category, catId);
        if (isUpdated) {
            // Return success message if the category was updated successfully
            return new ResponseEntity<>("Category updated successfully!", HttpStatus.OK);
        } else {
            // Return NOT_FOUND status if the category was not found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Delete category by its id
     * @param companyId The ID of the company
     * @param catId The ID of category to delete
     * @return ResponseEntity with success message if category was deleted successfully,
     *         or NOT_FOUND status if the category or company is not found.
     **/
    @DeleteMapping("/{catId}")
    private ResponseEntity<String> deleteCategoryById(@PathVariable Long companyId,
                                                      @PathVariable Long catId) {

        boolean isDeleted = categoryService.deleteCategoryById(companyId, catId);
        if (isDeleted) {
            return new ResponseEntity<>("Category deleted successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}