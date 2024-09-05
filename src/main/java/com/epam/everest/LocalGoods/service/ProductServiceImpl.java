package com.epam.everest.LocalGoods.service;


import com.epam.everest.LocalGoods.Handler.CategoryException;
import com.epam.everest.LocalGoods.dto.ProductDTO;
import com.epam.everest.LocalGoods.entity.ProductEntity;
import com.epam.everest.LocalGoods.entity.UserEntity;
import com.epam.everest.LocalGoods.repository.ProductRepository;
import com.epam.everest.LocalGoods.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public ProductDTO convertEntityToDTO(ProductEntity product) throws SQLException {
        return ProductDTO.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .stock(product.getStock())
                .image(product.getImage().getBytes(1, (int) product.getImage().length()))
                .category(product.getCategory())
                .build();
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO, Long vendorId, MultipartFile file) throws Exception {
        ProductEntity productEntity = objectMapper.convertValue(productDTO, ProductEntity.class);

        UserEntity userEntity = userRepository.findById(vendorId).orElseThrow(() -> new UsernameNotFoundException("User not found in the system"));
        productEntity.setUserEntity(userEntity);

        userEntity.getCategories().stream()
                .filter(categoryEntity -> categoryEntity.getCategoryName().equals(productDTO.getCategory()))
                .findFirst()
                .orElseThrow(() -> new CategoryException("Category not found"));

        byte[] bytes = file.getBytes();
        Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
        productEntity.setImage(blob);

        ProductEntity product = productRepository.save(productEntity);

        return convertEntityToDTO(product);
    }

    @Override
    public List<ProductDTO> getProduct(Long vendorId) {
        UserEntity userEntity = userRepository.findById(vendorId).orElseThrow(() -> new UsernameNotFoundException("User not found in the system"));
        List<ProductEntity> products = userEntity.getProducts();

        return products.stream()
                .map(productEntity -> {
                    try {
                        return convertEntityToDTO(productEntity);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<ProductEntity> products = productRepository.findAll();
        return products.stream()
                .map(productEntity -> {
                    try {
                        return convertEntityToDTO(productEntity);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO, MultipartFile file) throws Exception {
        ProductEntity existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existingProduct.setProductName(productDTO.getProductName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setQuantity(productDTO.getQuantity());
        existingProduct.setStock(productDTO.getStock());
        existingProduct.setCategory(productDTO.getCategory());

        if (file != null && !file.isEmpty()) {
            byte[] bytes = file.getBytes();
            Blob blob = new SerialBlob(bytes);
            existingProduct.setImage(blob);
        }

        ProductEntity updatedProduct = productRepository.save(existingProduct);
        return convertEntityToDTO(updatedProduct);
    }

    @Override
    public void deleteProduct(Long productId){
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(product);

    }

    @Override
    public ProductDTO getProductByName(String productName) throws SQLException {
        ProductEntity product = productRepository.findByProductName(productName)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return convertEntityToDTO(product);
    }
}


