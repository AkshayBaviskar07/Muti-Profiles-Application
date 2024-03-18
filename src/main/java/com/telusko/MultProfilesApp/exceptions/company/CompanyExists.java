package com.telusko.MultProfilesApp.exceptions.company;

public class CompanyExists extends RuntimeException{
    public CompanyExists(String message){
        super(message);
    }
}
