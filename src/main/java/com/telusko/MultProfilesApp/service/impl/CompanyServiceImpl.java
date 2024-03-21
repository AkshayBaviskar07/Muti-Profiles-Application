package com.telusko.MultProfilesApp.service.impl;

import com.telusko.MultProfilesApp.dao.CompanyRepo;
import com.telusko.MultProfilesApp.exceptions.company.CompanyExists;
import com.telusko.MultProfilesApp.exceptions.company.CompanyNotFound;
import com.telusko.MultProfilesApp.model.Category;
import com.telusko.MultProfilesApp.model.Company;
import com.telusko.MultProfilesApp.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepo companyRepo;
    @Autowired
    private CategoryServiceImpl categoryService;

    /**
     * Retrieves all companies from the database.
     * @return List of Company objects
     */
    @Override
    public List<Company> getAllCompanies() {
        List<Company> companies = companyRepo.findAll();

        if(!companies.isEmpty()) {
            return companies;
        } else {
            throw new CompanyNotFound("Company not found");
        }
    }

    /**
     * Saves the company object if the company with similar name does not exist
     * @param company the Company object to save
     * @return a success message if saved
     *      or if name already exist then return a failure message
     **/
    @Override
    public String addCompany(Company company) {
        // Checks if company name already exist
        if(companyRepo.findByName(company.getName()) == null){
            // Saves the object if name is unique
            companyRepo.save(company);
            return "Information saved successfully!";
        } else {
            // Return a failure message if name already exists
            throw new CompanyExists("Name already exists! Please try another name");
        }
    }

    /**
     * Retrieves a company by its ID.
     * @param id The ID of the company.
     * @return The company with the specified ID, or null if not found.
     */
    @Override
    public Company getCompanyById(Long id) {
        // Retrieve the company from the repository by its ID
        Optional<Company> companyOptional = companyRepo.findById(id);

        // Check if the company is present in the optional
        if(companyOptional.isPresent()) {
            // Get the company from the optional
            Company company = companyOptional.get();
            return company;
        } else{
            // Company not found, return null
            throw new CompanyNotFound("Company not found");
        }
    }

    /**
     * Updates the details of a company in the database.
     *
     * @param updatedCompany The updated details of the company
     * @param id The ID of the company to update.
     * @return true if company updated successfully, false otherwise.
     **/
    @Override
    public boolean updateCompanyDetails(Company updatedCompany, Long id) {

        // Get optional company by id
        Optional<Company> companyOptional = companyRepo.findById(id);
        // Check if company exists
        if(companyOptional.isPresent()){

            // Get the company object
            Company company = companyOptional.get();
            // Update the details
            company.setName(updatedCompany.getName());
            company.setCity(updatedCompany.getCity());
            company.setState(updatedCompany.getState());

            // Save the updated company
            companyRepo.save(company);
            // Return true if updated successfully
            return true;
        } else {
            // Return false if company not found.
            throw new CompanyNotFound("Company not found");
        }
    }

    /**
     *  Deletes company details by id
     *
     * @param id The id of company to be deleted
     * @return true if company deleted successfully or false otherwise.
     **/
    @Override
    public boolean deleteCompanyById(Long id) {

        // Get optional company by id
        Optional<Company> companyOptional  = companyRepo.findById(id);
        // Check if company exists
        if (companyOptional.isPresent()) {
            // If exists delete the record.
            companyRepo.deleteById(id);
            // Return true if record deleted successfully.
            return true;
        } else {
            // Return false if company not found.
            throw new CompanyNotFound("Company not found");
        }
    }

    /** Adds the specified category to the company.
     * @param companyId the ID of the company
     * @param catId the ID of the category to be added
     * @return true if the category was added successfully, false otherwise
     */
    @Override
    public boolean addCategoryInCompany(Long companyId, Long catId) {
        // Fetch category by id
        Category category = categoryService.getCategoryById(catId);
        // Fetch company by id
        Company existingCompany = getCompanyById(companyId);

        // Check is company found or not
        if (existingCompany != null) {
            // Add category in company
            existingCompany.getCategories().add(category);
            category.getCompanies().add(existingCompany);
            // Save company and return true
            companyRepo.save(existingCompany);
            categoryService.updateCategory(category, catId);
            return true;
        } else {
            // Return false if category not added
            return false;
        }
    }

    /**
     * Removes a category from a company by updating the company's category list and the category's company list.
     * @param companyId The ID of the company
     * @param catId The ID of the category
     * @return true if the category is successfully removed from the company, false otherwise
     */
    @Override
    public boolean removeCategoryFromCompany(Long companyId, Long catId) {

        // Get company by its ID
        Company existingCompany = getCompanyById(companyId);
        // Get category by its ID
        Category category = categoryService.getCategoryById(catId);

        // Check if company exits or not
        if (existingCompany != null) {
            existingCompany.getCategories().remove(category);   // Remove category from company
            category.getCompanies().remove(existingCompany);    // Remove company from category
            companyRepo.save(existingCompany);      // Update company details
            categoryService.updateCategory(category, catId);    // Update category details
            return true;    // Return true if category successfully removed
        } else {
            // Return false if category not removed
            return false;
        }
    }
}
