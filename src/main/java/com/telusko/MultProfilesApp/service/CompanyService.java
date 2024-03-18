package com.telusko.MultProfilesApp.service;

import com.telusko.MultProfilesApp.model.Company;

import java.util.List;

public interface CompanyService {

    List<Company> getAllCompanies();

    String addCompany(Company company);

    Company getCompanyById(Long id);

    boolean updateCompanyDetails(Company updatedCompany, Long id);

    boolean deleteCompanyById(Long id);
}
