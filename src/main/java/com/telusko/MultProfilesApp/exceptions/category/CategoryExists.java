package com.telusko.MultProfilesApp.exceptions.category;

public class CategoryExists extends RuntimeException{
    public CategoryExists(String message) {
        super(message);
    }
}
