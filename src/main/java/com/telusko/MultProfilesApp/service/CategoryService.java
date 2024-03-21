package com.telusko.MultProfilesApp.service;

import com.telusko.MultProfilesApp.model.Category;

import java.util.List;

public interface CategoryService {

    /**
     * Retrieves all categories for a given company.
     * @param companyId the ID of the company to retrieve categories for
     * @return a list of Category objects representing all categories for the company
     */
    List<Category> getAllCategories(Long companyId);

    /**
     * Adds a category to the specified company.
     * @param category The category to add.
     * @param companyId The ID of the company to add the category to.
     * @return true if the category was successfully added, false otherwise.
     */
    boolean addCategory(Category category, Long companyId);

    /**
     * Retrieve the category by its ID for the given company.
     * @param companyId the ID of the company
     * @param catId the ID of the category to retrieve
     * @return the category object corresponding to the catId for the given companyId
     */
    Category getCategoryById(Long companyId, Long catId);

    /**
     * Updates a category for a given company and category ID.
     * @param companyId the ID of the company
     * @param updatedCategory the updated category
     * @param id the ID of the category to be updated
     * @return true if the category was successfully updated, false otherwise
     */
    boolean updateCategory(Long companyId, Category updatedCategory, Long id);

    /**
     * Deletes a category by its ID for a given company.
     * @param companyId the ID of the company
     * @param catId the ID of the category to delete
     * @return true if the category was successfully deleted, false otherwise
     */
    boolean deleteCategoryById(Long companyId, Long catId);

}
