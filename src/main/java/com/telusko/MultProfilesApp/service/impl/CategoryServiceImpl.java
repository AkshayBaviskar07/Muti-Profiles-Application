package com.telusko.MultProfilesApp.service.impl;

import com.telusko.MultProfilesApp.dao.CategoryRepo;
import com.telusko.MultProfilesApp.exceptions.category.CategoryExists;
import com.telusko.MultProfilesApp.exceptions.category.CategoryNotFound;
import com.telusko.MultProfilesApp.exceptions.company.CompanyNotFound;
import com.telusko.MultProfilesApp.model.Category;
import com.telusko.MultProfilesApp.model.Company;
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
    private CompanyServiceImpl companyService;

    /**
     * Retrieves all categories for a given company from the database.
     * @param companyId the id of the company to retrieve categories for
     * @return a list of all categories for the company
     * @throws CompanyNotFound if the company with the given id is not found
     * @throws CategoryNotFound if no categories are found for the company
     */
    @Override
    public List<Category> getAllCategories(Long companyId) {
        // Retrieve the company based on companyId
        Company company = companyService.getCompanyById(companyId);

        // Check if the company exists
        if(company != null) {
            List<Category> categories = company.getCategories();

            // Check if categories exist for the company
            if(categories.isEmpty()) {
                throw new CategoryNotFound("Categories not available for this company");
            } else {
                return categories;
            }
        } else {
            throw new CompanyNotFound("Company Not Found");
        }
    }

    /**
     * Adds a new category to the repository if it does not already exist.
     * @param category the category to be added
     * @param companyId the ID of the company to which the category belongs
     * @return true if the category was added successfully, false otherwise
     * @throws CategoryExists if the category name already exists in the repository
     * @throws CompanyNotFound if the company with the given ID is not found
     */
    @Override
    public boolean addCategory(Category category, Long companyId) {
        // Get the company by ID
        Company company = companyService.getCompanyById(companyId);

        // Check if the company exists
        if (company != null) {
            // Add the category to the company's list of categories
            company.getCategories().add(category);

            // Update the company details
            companyService.updateCompanyDetails(company, companyId);

            // Save the category to the repository
            categoryRepo.save(category);

            return true;
        } else {
            // Throw an exception if the company is not found
            throw new CompanyNotFound("Company not found");
        }
    }

    /**
     * Retrieves the category with the specified ID from the repository.
     * @param companyId the ID of the company
     * @param catId the ID of the category
     * @return the category with the specified ID
     * @throws CategoryNotFound if the category with the specified ID is not found
     * @throws CompanyNotFound if the company with the specified ID is not found
     */
    @Override
    public Category getCategoryById(Long companyId, Long catId) {
        // Retrieve the company by ID
        Company company = companyService.getCompanyById(companyId);

        // Check if the company exists
        if(company != null) {
            List<Category> categories = company.getCategories();

            // Check if the company has categories
            if(!categories.isEmpty()) {
                // Find the category by ID
                return categories.stream()
                        .filter(category -> category.getId().equals(catId))
                        .findFirst()
                        .orElseThrow(() -> new CategoryNotFound("Category Not Found"));
            } else {
                // Throw exception if no categories available
                throw new CategoryNotFound("Category not available");
            }
        } else {
            // Throw exception if company not found
            throw new CompanyNotFound("Company Not Found");
        }
    }

    /**
     * Updates the category with the given ID by replacing its name and type with the values from the updatedCategory object.
     * If the category with the specified ID is found, it updates the category and returns true.
     * If the category is not found, it throws a CategoryNotFound exception.
     * @param companyId The ID of the company to which the category belongs.
     * @param updatedCategory The Category object containing the updated name, type, companies, and products.
     * @param catId The ID of the category to update.
     * @return true if the category is updated successfully.
     * @throws CategoryNotFound if the category with the specified ID is not found.
     * @throws CompanyNotFound if the company with the specified ID is not found.
     */
    @Override
    public boolean updateCategory(Long companyId, Category updatedCategory, Long catId) {
        // Get the company by ID
        Company company = companyService.getCompanyById(companyId);

        // Check if the company exists
        if(company != null) {
            // Find the category by ID within the company's categories
            Optional<Category> availableCat = company.getCategories()
                    .stream()
                    .filter(category -> category.getId().equals(catId))
                    .findFirst();

            // If the category is found
            if(availableCat.isPresent()) {
                // Update the category details
                availableCat.get().setName(updatedCategory.getName());
                availableCat.get().setType(updatedCategory.getType());
                availableCat.get().setCompanies(updatedCategory.getCompanies());
                availableCat.get().setProducts(updatedCategory.getProducts());

                // Save the updated category
                categoryRepo.save(availableCat.get());
                // Update the company details
                companyService.updateCompanyDetails(company, companyId);
                return true;
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
     * Deletes a category by its ID.
     * @param companyId The ID of the company.
     * @param catId The ID of the category to delete.
     * @return True if the category was successfully deleted, false otherwise.
     * @throws CategoryNotFound If the category with the given ID does not exist.
     * @throws CompanyNotFound If the company with the given ID does not exist.
     */
    @Override
    public boolean deleteCategoryById(Long companyId, Long catId) {
        // Get the company by its ID
        Company company = companyService.getCompanyById(companyId);

        // Check if the company exists
        if(company != null) {
            // Find the category by its ID
            Optional<Category> availableCat = company.getCategories()
                    .stream()
                    .filter(category -> category.getId().equals(catId))
                    .findFirst();

            // Check if the category exists
            if(availableCat.isPresent()) {
                // Remove the category from the company's list of categories
                Category category = availableCat.get();
                company.getCategories().remove(category);

                // Delete the category from the repository
                categoryRepo.deleteById(catId);

                // Update the company details
                companyService.updateCompanyDetails(company, companyId);
                return true;
            } else {
                // Throw an exception if the category is not found
                throw new CategoryNotFound("Category Not Found");
            }
        } else {
            // Throw an exception if the company is not found
            throw new CompanyNotFound("Company Not Found");
        }
    }
}
