package com.example.products.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Category {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long categoryId;
    private String categoryname;


    public Category() {
    }

    public Category(Long categoryId, String categoryname) {
        this.categoryId = categoryId;
        this.categoryname = categoryname;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String name) {
        this.categoryname = name;
    }
}
