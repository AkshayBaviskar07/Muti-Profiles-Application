package com.telusko.MultProfilesApp.exceptions.product;

public class ProductNotFound extends RuntimeException{
    public ProductNotFound(String message) {
        super(message);
    }
}
