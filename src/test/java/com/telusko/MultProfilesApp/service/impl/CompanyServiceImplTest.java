package com.telusko.MultProfilesApp.service.impl;

import com.telusko.MultProfilesApp.dao.CompanyRepo;
import com.telusko.MultProfilesApp.exceptions.company.CompanyExists;
import com.telusko.MultProfilesApp.exceptions.company.CompanyNotFound;
import com.telusko.MultProfilesApp.model.Company;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompanyServiceImplTest {
    @InjectMocks
    private CompanyServiceImpl companyService;
    @Mock
    private CompanyRepo companyRepo;
    Company company = null;

    @BeforeEach
    void setUp() {
        company = new Company();
        company.setName("Tata");
        company.setCity("Mumbai");
        company.setState("Maharashtra");
    }

    @Test
    void test_getAllCompanies() {
        // when
        when(companyRepo.findAll()).thenReturn(List.of(company));
       // then
       assertEquals(1, companyService.getAllCompanies().size());
    }

    @Test
    void shouldThrowCompanyNotFoundWhenGetAllCompanies() {
        when(companyRepo.findAll()).thenReturn(new ArrayList<>());
        assertThrows(CompanyNotFound.class, () -> companyService.getAllCompanies());
    }

    @Test
    void test_addCompany() {
        when(companyRepo.save(company)).thenReturn(company);
        assertEquals("Information saved successfully!", companyService.addCompany(company));
    }

    @Test
    void shouldThrowCompanyExistsWhenAddCompany() {
        when(companyRepo.findByName(company.getName())).thenReturn(company);
        assertThrows(CompanyExists.class, () -> companyService.addCompany(company));
    }

    @Test
    void test_getCompanyById() {
        when(companyRepo.findById(1L)).thenReturn(Optional.of(company));
        assertEquals(company, companyService.getCompanyById(1L));
    }

    @Test
    void shouldThrowCompanyNotFoundWhenGetCompanyById() {
        when(companyRepo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(CompanyNotFound.class, () -> companyService.getCompanyById(1L));
    }

    @Test
    void test_updateCompanyDetails() {
        when(companyRepo.findById(1L)).thenReturn(Optional.of(company));
        assertTrue(companyService.updateCompanyDetails(company, 1L));
    }

    @Test
    void shouldThrowCompanyNotFoundWhenUpdateCompanyDetails() {
        Long invalidId = -1L;
        when(companyRepo.findById(invalidId)).thenReturn(Optional.empty());
        assertThrows(CompanyNotFound.class, () -> companyService.updateCompanyDetails(company, invalidId));
    }

    @Test
    void deleteCompanyById() {
        when(companyRepo.findById(1L)).thenReturn(Optional.of(company));
        assertTrue(companyService.deleteCompanyById(1L));
    }

    @Test
    void shouldThrowCompanyNotFoundWhenDeleteCompanyById() {
        Long invalidId = -1L;
        when(companyRepo.findById(invalidId)).thenReturn(Optional.empty());
        assertThrows(CompanyNotFound.class, () -> companyService.deleteCompanyById(invalidId));
    }
}