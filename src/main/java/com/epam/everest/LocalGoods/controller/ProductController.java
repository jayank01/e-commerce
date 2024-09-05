package com.epam.everest.LocalGoods.controller;

import com.epam.everest.LocalGoods.dto.ProductDTO;
import com.epam.everest.LocalGoods.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;


@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService productService;

    @PostMapping({"/{vendorId}"})
    public ResponseEntity<?> createProduct(@RequestPart("product") ProductDTO productDTO, @PathVariable Long vendorId, @RequestParam("image") MultipartFile file) throws Exception {
        return new ResponseEntity<>(productService.createProduct(productDTO, vendorId, file), HttpStatus.CREATED);
    }

    @GetMapping({"/{vendorId}"})
    public ResponseEntity<?> getProduct(@PathVariable Long vendorId) {
        return new ResponseEntity<>(productService.getProduct(vendorId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/search/{productName}")
    public ResponseEntity<?> getProductByName(@PathVariable String productName) throws SQLException {
        return new ResponseEntity<>(productService.getProductByName(productName), HttpStatus.OK);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable Long productId,
            @Valid @RequestPart("product") ProductDTO productDTO,
            @RequestPart(value = "file", required = false) MultipartFile file) throws Exception {

        ProductDTO updatedProduct = productService.updateProduct(productId, productDTO, file);
        return ResponseEntity.ok(updatedProduct);
    }


    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) throws Exception {
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
