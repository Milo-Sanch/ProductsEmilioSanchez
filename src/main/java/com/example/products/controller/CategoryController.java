package com.example.products.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.example.products.repository.CategoryRepository;
import com.example.products.dto.CategoryRequest;
import com.example.products.dto.CategoryResponse;
import com.example.products.model.Category;

import jakarta.validation.Valid;


import java.util.List;
import java.util.Optional;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping ("/categories")
public class CategoryController {

    private final CategoryRepository repoCategory;

    public CategoryController(CategoryRepository repoCategory){
        this.repoCategory = repoCategory;
    }

    @PostMapping
    public ResponseEntity<Category> save(@RequestBody Category category) {
        return ResponseEntity.ok(
                repoCategory.save(category)
        );
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAll() {
        return ResponseEntity.ok(
                repoCategory.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findById(@PathVariable Long id) {
        Optional<Category> category = repoCategory.findById(id);
        if (category.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(toResponse(category.get()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable Long id, @Valid @RequestBody CategoryRequest categoryUpdated) {
        if (!repoCategory.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Category entity = toEntity(categoryUpdated);
        entity.setCategoryId(id);
        Category savedCategory = repoCategory.save(entity);
        return ResponseEntity.ok(toResponse(savedCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repoCategory.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repoCategory.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private CategoryResponse toResponse(Category category) {
        return new CategoryResponse(category.getCategoryId(), category.getCategoryname());
    }

    private Category toEntity(CategoryRequest category) {
        Category categoryEntity = new Category();
        categoryEntity.setCategoryname(category.name());
        return categoryEntity;
    }

}
