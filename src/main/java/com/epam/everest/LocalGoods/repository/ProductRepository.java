package com.epam.everest.LocalGoods.repository;

import com.epam.everest.LocalGoods.dto.ProductDTO;
import com.epam.everest.LocalGoods.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity,Long>{

    Optional<ProductEntity> findByProductName(String productName);
}
