package com.telusko.MultProfilesApp.dao;

import com.telusko.MultProfilesApp.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

    Category findByName(String categoryName);
}
