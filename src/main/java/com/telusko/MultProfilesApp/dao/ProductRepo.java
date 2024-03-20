package com.telusko.MultProfilesApp.dao;

import com.telusko.MultProfilesApp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    Product findByName(String name);
}
