package edu.aitu.oop3.db.commonInfrastructure.services;

public abstract class BaseService { 
    protected void log(String message) {
        System.out.println("[SERVICE] " + message);
    }
}