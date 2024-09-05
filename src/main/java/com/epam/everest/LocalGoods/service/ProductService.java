package com.epam.everest.LocalGoods.service;

import com.epam.everest.LocalGoods.dto.ProductDTO;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO, Long vendorId, MultipartFile file) throws Exception;
    List<ProductDTO> getProduct(Long vendorId);
    List<ProductDTO> getAllProducts();
    ProductDTO updateProduct(Long productId, ProductDTO productDTO, MultipartFile file) throws Exception;
    void deleteProduct(Long productId);
    ProductDTO getProductByName(String productName) throws SQLException;


}
