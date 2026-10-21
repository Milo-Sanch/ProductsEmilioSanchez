package com.example.products.controller;

import com.example.products.dto.PercentajeRequest;
import com.example.products.dto.ProductRequest;
import com.example.products.dto.ProductResponse;
import com.example.products.exception.ResourceConflict;
import com.example.products.exception.ResourceNotFoundException;
import com.example.products.model.Product;
import com.example.products.service.ProductService;


import jakarta.validation.Valid;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/products")
public class ProductController {


    private final ProductService service;


    public ProductController(ProductService service){
        this.service = service;
    }


    @GetMapping ()
    public ResponseEntity<List<ProductResponse>> findAll() {
        List<ProductResponse> lista = new ArrayList<>();
        for(Product elem: service.findAll()){
            lista.add(toResponse(elem));
        }
        return ResponseEntity.ok(lista) ;
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
        Product product =service.findById(id).orElseThrow(
                ()->
                        new ResourceNotFoundException("Producto no encontrado" + id)
        );

        return ResponseEntity.ok(toResponse(product));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> save(@Valid @RequestBody ProductRequest product) {


        Product newProduct =service.save(toEntity(product),product.categoryId());
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(newProduct));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id,@Valid @RequestBody ProductRequest productUpdated) {
        Product product1 =service.update(
                id,
                toEntity( productUpdated),
                productUpdated.categoryId()
        );
        return ResponseEntity.ok(toResponse(product1));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        boolean deleted = service.delete(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public  ResponseEntity<ProductResponse> updatePrice (@PathVariable Long id, @Valid @RequestBody PercentajeRequest percentajeRequest) {
        Product product =service.findById(id).orElseThrow(
                ()->
                        new ResourceNotFoundException("Producto no encontrado" + id)
        );

        double percentaje = percentajeRequest.getPercentaje();

        if (product.getStock() == 0) {
            throw new ResourceConflict("No se permite un descuento si el stock esta en 0");
        }

        double cantidadDescontada  = product.getPrice() * ( 1 - (percentaje / 100));
        double precioDescontado = product.getPrice() - cantidadDescontada;
        product.setPrice(precioDescontado);

        service.update(id,product,product.getCategory().getCategoryId());

        return ResponseEntity.ok(toResponse(product));
    }


    private ProductResponse toResponse(Product product){
        return new ProductResponse(product.getId(),product.getName(),product.getPrice(),product.getStock(),0L,"");
    }


    private Product toEntity(ProductRequest product){
        Product productEntity = new Product();
        productEntity.setName(product.name());
        productEntity.setPrice(product.price());
        productEntity.setStock(product.stock());
        return productEntity;
    }
}
