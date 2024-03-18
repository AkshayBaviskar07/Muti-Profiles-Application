package com.telusko.MultProfilesApp.service.impl;

import com.telusko.MultProfilesApp.dao.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl {
    @Autowired
    private ProductRepo productRepo;
}
