package com.telusko.MultProfilesApp.controller;

import com.telusko.MultProfilesApp.model.Company;
import com.telusko.MultProfilesApp.service.impl.CompanyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    private CompanyServiceImpl companyService;

    /**
    * Endpoint to retrieve all companies
     *
     * @return   ResponseEntity with a list of companies objects if found with StatusCode(OK -> 200) or (NOT_FOUND -> 400)
    */
    @GetMapping
    private ResponseEntity<List<Company>> getAllCompanies() {

        List<Company> companies = companyService.getAllCompanies();
        if (!companies.isEmpty())
            return new ResponseEntity<>(companies, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Endpoint to Save Company record
     *
     * @param company the Company object to add
     * @return ResponseEntity with success message and StatusCode(CREATED -> 201) if the company added
     *          or StatusCode(INTERNAL_SERVER_ERROR -> 500 ) if there was problem in saving data
    */
    @PostMapping
    private ResponseEntity<String> addCompany(@RequestBody Company company){

        String isAdded = companyService.addCompany(company);
        if(!isAdded.isEmpty())
            return new ResponseEntity<>(isAdded, HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Endpoint to Get Company object by id
     *
     * @param id to fetch the record of particular id
     * @return ResponseEntity with Company object and Http Status (OK->200)
     *      or (NOT_FOUND->400)
    **/
    @GetMapping("/{id}")
    private ResponseEntity<Company> getCompanyById(@PathVariable Long id) {

        Company company = companyService.getCompanyById(id);
        if(company != null) {
            return new ResponseEntity<>(company, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint to Update company details by ID.
     *
     * @param updatedCompany The updated company details
     * @param id The ID of the company to be updated
     * @return ResponseEntity with a success message and StatusCode(200) if the update was successful,
     *         or NOT_FOUND(400) status if the company was not found
     */
    @PutMapping("/{id}")
    private ResponseEntity<String> updateCompanyDetails(@RequestBody Company updatedCompany,
                                                        @PathVariable Long id) {

        boolean isUpdated = companyService
                .updateCompanyDetails(updatedCompany, id);

        if(isUpdated) {
            return new ResponseEntity<>("Information updated successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint to Delete Company details by ID
     * @param id The id of the company to delete
     * @return ResponseEntity with a success message and StatusCode(200) if the record deleted
     *      or NOT_FOUND(400) if the company not found.
     **/
    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteCompanyById(@PathVariable Long id) {

        boolean isDeleted = companyService.deleteCompanyById(id);
        if (isDeleted) {
            return new ResponseEntity<>("Record deleted!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
