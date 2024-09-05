package com.epam.everest.LocalGoods.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    @Positive(message = "Category id should be positive")
    private Long id;

    @NotBlank(message = "Category name is mandatory")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Category name must contain only letters")
    private String categoryName;

}
