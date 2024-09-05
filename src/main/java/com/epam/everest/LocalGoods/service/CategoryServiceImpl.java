package com.epam.everest.LocalGoods.service;

import com.epam.everest.LocalGoods.Handler.CategoryException;
import com.epam.everest.LocalGoods.dto.CategoryDTO;
import com.epam.everest.LocalGoods.entity.CategoryEntity;
import com.epam.everest.LocalGoods.entity.UserEntity;
import com.epam.everest.LocalGoods.repository.CategoryRepository;
import com.epam.everest.LocalGoods.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
    @RequiredArgsConstructor
    public class CategoryServiceImpl implements CategoryService{

        private final ObjectMapper objectMapper;
        private final UserRepository userRepository;
        private final CategoryRepository categoryRepository;

        @Override
        public CategoryDTO createCategory(CategoryDTO categoryDTO, Long vendorId) throws CategoryException {
            UserEntity userEntity = userRepository.findById(vendorId).orElseThrow(() -> new UsernameNotFoundException("User not found in the system"));

            Optional<CategoryEntity> categoryEntity = userEntity.getCategories().stream()
                    .filter(category -> category.getCategoryName().equals(categoryDTO.getCategoryName()))
                    .findFirst();

            if(categoryEntity.isPresent()){
                throw new CategoryException("Category already exists");
            }else {
                CategoryEntity Category = objectMapper.convertValue(categoryDTO, CategoryEntity.class);
                Category.setUserEntity(userEntity);
                return objectMapper.convertValue(categoryRepository.save(Category), CategoryDTO.class);
            }
        }

        @Override
        public List<CategoryDTO> getCategory(Long vendorId){
            UserEntity userEntity = userRepository.findById(vendorId).orElseThrow(() -> new UsernameNotFoundException("User not found in the system"));
            List<CategoryEntity> categories = userEntity.getCategories();

            return categories.stream()
                    .map(categoryEntity -> objectMapper.convertValue(categoryEntity, CategoryDTO.class))
                    .collect(Collectors.toList());
        }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<CategoryEntity> categories = categoryRepository.findAll();
        return categories.stream()
                .map(categoryEntity -> objectMapper.convertValue(categoryEntity, CategoryDTO.class))
                .collect(Collectors.toList());
    }
}
