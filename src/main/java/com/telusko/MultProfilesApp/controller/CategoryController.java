package com.telusko.MultProfilesApp.controller;

import com.telusko.MultProfilesApp.model.Category;
import com.telusko.MultProfilesApp.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company/category")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    /**
     * Retrieves all categories.
     *
     * @return ResponseEntity with a list of categories and HTTP status
     */
    @GetMapping
    private ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        if (!categories.isEmpty()) {
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint to add a new category.
     *
     * @param category the category to be added
     * @return ResponseEntity with a success message if the category was added successfully, or an error status otherwise
     */
    @PostMapping
    private ResponseEntity<String> addCategory(@RequestBody Category category) {
        boolean isAdded = categoryService.addCategory(category);
        if (isAdded) {
            return new ResponseEntity<>("Category added successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get category by ID
     * @param id The ID of the category
     * @return The category with the specified ID, or NOT_FOUND if not found
     */
    @GetMapping("/{id}")
    private ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        if(category != null) {
            return new ResponseEntity<>(category, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Updates a category by its ID.
     *
     * @param category The updated category object.
     * @param id The ID of the category to update.
     * @return ResponseEntity with a success message if the category was updated successfully,
     *         or NOT_FOUND status if the category was not found.
     */
    @PutMapping("/{id}")
    private ResponseEntity<String> updateCategoryById(@RequestBody Category category,
                                                      @PathVariable Long id) {

        boolean isUpdated = categoryService.updateCategory(category, id);
        if (isUpdated) {
            return new ResponseEntity<>("Category updated successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Delete category by its id
     *
     * @param id The ID of category to delete
     * @return ResponseEntity with success message if category was deleted successfully
     *          or NOT_FOUND status if the company not found.
     **/
    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteCategoryById(@PathVariable Long id) {

        boolean isDeleted = categoryService.deleteCategoryById(id);
        if (isDeleted) {
            return new ResponseEntity<>("Category deleted successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{catId}/product/{prodId}")
    private ResponseEntity<String> addProductInCategory(@PathVariable Long catId,
                                                        @PathVariable Long prodId) {

        boolean isProductAdded = categoryService.addProductInCategory(catId, prodId);
        if(isProductAdded) {
            return new ResponseEntity<>("Product added successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{catId}/product/{prodId}")
    private ResponseEntity<String> removeProductFromCategory(@PathVariable Long prodId,
                                                             @PathVariable Long catId) {

        boolean isProductRemoved = categoryService.removeProductFromCategory(prodId, catId);
        if (isProductRemoved) {
            return new ResponseEntity<>("Product removed successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}