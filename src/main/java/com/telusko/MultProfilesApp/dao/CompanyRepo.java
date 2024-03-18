package com.telusko.MultProfilesApp.dao;

import com.telusko.MultProfilesApp.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepo extends JpaRepository<Company, Long> {

    Company findByName(String name);
}
