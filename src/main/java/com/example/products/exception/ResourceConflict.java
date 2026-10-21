package com.example.products.exception;

public class ResourceConflict extends RuntimeException {
    public ResourceConflict(String message) {
        super(message);
    }
}
