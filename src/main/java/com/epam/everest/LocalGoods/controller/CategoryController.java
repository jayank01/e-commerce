package com.epam.everest.LocalGoods.controller;

import com.epam.everest.LocalGoods.Handler.CategoryException;
import com.epam.everest.LocalGoods.dto.CategoryDTO;
import com.epam.everest.LocalGoods.dto.Message;
import com.epam.everest.LocalGoods.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/{vendorId}")
    public ResponseEntity<?> addCategory(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable Long vendorId) throws CategoryException {
        return new ResponseEntity<>(categoryService.createCategory(categoryDTO, vendorId), HttpStatus.CREATED);
    }

    @GetMapping("/{vendorId}")
    public ResponseEntity<?> getCategory(@PathVariable Long vendorId) {
        return new ResponseEntity<>(categoryService.getCategory(vendorId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }

}

