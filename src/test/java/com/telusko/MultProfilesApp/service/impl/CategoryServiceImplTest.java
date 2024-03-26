package com.telusko.MultProfilesApp.service.impl;

import com.telusko.MultProfilesApp.dao.CategoryRepo;
import com.telusko.MultProfilesApp.model.Category;
import com.telusko.MultProfilesApp.model.Company;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    @InjectMocks
    private CategoryServiceImpl underTest;
    @Mock
    private CategoryRepo categoryRepo;
    @Mock
    private CompanyServiceImpl companyService;
    Company company=null;
    Category category=null;

    @BeforeEach
    void setUp() {
        company = new Company();
        company.setId(1L);
        company.setName("Tata");
        company.setCity("Mumbai");
        company.setState("Maharashtra");

        category = new Category();
        category.setId(1L);
        category.setName("Electronics");
        category.setType("Electronics");

        List<Category> categories = new ArrayList<>();
        categories.add(category);
        company.setCategories(categories);
    }

    @AfterEach
    void tearDown() {
        company = null;
        category = null;
    }

    /**
     * Test to get all categories.
     */
    @Test
    void testGetAllCategories() {
        // Given
        when(companyService.getCompanyById(1L)).thenReturn(company);

        // Then
        assertEquals(List.of(category), underTest.getAllCategories(1L));
        assertEquals(1, underTest.getAllCategories(1L).size());
    }

    /**
     * Test to add category in company
     *
     * @param companyId The ID of the company
     * @param categoryId The ID of the category
     * @return true if category added successfully, false otherwise
     */
    @Test
    void addCategory() {
        // when
        // Mock the behaviour of the company service to return the company by its ID.
        when(companyService.getCompanyById(1L)).thenReturn(company);

        // then
        boolean actualResult = underTest.addCategory(category, 1L);

        // assertion
        // Verifying that the company saved or not
        assertTrue(actualResult);
        // Verifying that the company contains category.
        assertTrue(company.getCategories().contains(category));
        // Verify that the company service's updateCompanyDetails with company, ID
        verify(companyService, times(1)).updateCompanyDetails(company, 1L);
        // Verify that the category repository save's the category
        verify(categoryRepo).save(category);
    }

    /**
     *  Test for retrieving category by its ID.
     *
     * @param companyId The ID of the company
     * @param categoryId The ID of the category
     */
    @Test
    void getCategoryById() {
        // when
        // Mock the behaviour of company service to return the company.
        when(companyService.getCompanyById(1L)).thenReturn(company);

        // then
        // Ensuring that the retrieved category is not null.
        assertNotNull(underTest.getCategoryById(1L, 1L));

        // Verifying category ID.
        assertEquals(1L, category.getId());

        // Verifying that the correct category object is returned
        assertEquals(category, underTest.getCategoryById(1L, 1L));

        // Verifying that the company's category contains category
        assertTrue(company.getCategories().contains(category));
    }

    /**
     * Test for updating a category by its id
     *
     * @param companyId The ID of the company
     * @param categoryId The ID of the category
     * @return True if the category was successfully updated, false otherwise
     */
    @Test
    void updateCategory() {
        // Mock the behaviour of the company service to return the company when its ID requested.
        when(companyService.getCompanyById(company.getId())).thenReturn(company);

        // Call the updateCategory method and assert that it is true.
        assertTrue(underTest.updateCategory(company.getId(), category, category.getId()));

        // Verify that the category repository's save method was called with the correct category object
        verify(categoryRepo).save(category);

        // Verify that the company service's updateCompanyDetails method was called with the correct company object and ID.
        verify(companyService).updateCompanyDetails(company, company.getId());
    }

    /**
     * Test for deleting a category by its ID.
     *
     * @param companyId The ID of the company.
     * @param categoryId The ID of the category to be deleted.
     * @return True if the category was successfully deleted, false otherwise.
     */
    @Test
    void deleteCategoryById() {
        // Mock the behavior of the company service to return the company when its ID is requested.
        when(companyService.getCompanyById(company.getId())).thenReturn(company);

        // Assert that the category is contained in the company's categories list.
        assertTrue(company.getCategories().contains(category));

        // Call the deleteCategoryById method and assert that it returns true.
        assertTrue(underTest.deleteCategoryById(company.getId(), category.getId()));

        // Verify that the category repository's deleteById method was called with the correct category ID.
        verify(categoryRepo).deleteById(category.getId());

        // Verify that the company service's updateCompanyDetails method was called with the correct company and company ID.
        verify(companyService).updateCompanyDetails(company, company.getId());
    }
}