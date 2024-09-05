package com.epam.everest.LocalGoods.repository;

import com.epam.everest.LocalGoods.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findBycategoryName(String categoryName);

}
