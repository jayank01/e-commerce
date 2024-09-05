package com.epam.everest.LocalGoods.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Long Id;
    private String firstName;
    private String lastName;
    private String jwtToken;
    private String role;
    private String message;
}
