package com.telusko.MultProfilesApp.service;

import com.telusko.MultProfilesApp.model.Company;

import java.util.List;

public interface CompanyService {

    /**
     * Retrieves all the companies from the database.
     * @return a list of Company objects representing all the companies
     */
    List<Company> getAllCompanies();

    /**
     * Adds a company to the system
     * @param company The company object to be added.
     * @return The result of the operation.
     */
    String addCompany(Company company);

    /**
     * Retrieves a company by its ID
     * @param id The id of the company to retrieve
     * @return the company with the specified id or null if not found
    */
    Company getCompanyById(Long id);

    /**
     * Updates the details of a company.
     * @param updatedCompany the updated company details
     * @param id the ID of the company to update
     * @return true if the company details were successfully updated, false otherwise
     */
    boolean updateCompanyDetails(Company updatedCompany, Long id);

    /**
     * Deletes a company by its ID.
     * @param id the ID of the company to be deleted
     * @return true if the company is successfully deleted, false otherwise
     */
    boolean deleteCompanyById(Long id);


}
