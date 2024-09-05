package com.epam.everest.LocalGoods.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordDTO {
    private Integer otp;
    @NotEmpty(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$", message = "Password must contain at least one uppercase letter, one lowercase letter, and one digit")
    private String password;
    private String confirmPassword;
}
