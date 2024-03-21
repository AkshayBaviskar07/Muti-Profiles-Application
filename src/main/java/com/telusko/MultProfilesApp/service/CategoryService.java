package com.telusko.MultProfilesApp.service;

import com.telusko.MultProfilesApp.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories();
    boolean addCategory(Category category);
    Category getCategoryById(Long id);
    boolean updateCategory(Category updatedCategory, Long id);
    boolean deleteCategoryById(Long id);

    boolean addProductInCategory(Long catId, Long prodId);

    boolean removeProductFromCategory(Long prodId, Long catId);
}
