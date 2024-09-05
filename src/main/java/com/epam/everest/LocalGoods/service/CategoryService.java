package com.epam.everest.LocalGoods.service;

import com.epam.everest.LocalGoods.Handler.CategoryException;
import com.epam.everest.LocalGoods.dto.CategoryDTO;

import java.util.List;

public interface CategoryService{
    CategoryDTO createCategory(CategoryDTO categoryDTO, Long vendorId) throws CategoryException;
    List<CategoryDTO> getCategory(Long vendorId);
    List<CategoryDTO> getAllCategories();
}