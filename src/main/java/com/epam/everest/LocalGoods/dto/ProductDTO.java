package com.epam.everest.LocalGoods.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

        private Long id;
        private String productName;
        private String description;
        private double price;
        private int quantity;
        private String stock;
        private byte[] image;
        private String category;

}
