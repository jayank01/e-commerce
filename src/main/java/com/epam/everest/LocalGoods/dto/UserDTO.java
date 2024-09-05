package com.epam.everest.LocalGoods.dto;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private Long id;
    @NotBlank(message = "First name is mandatory")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "First name must contain only letters")
    private String firstName;
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Last name must contain only letters")
    @NotBlank(message = "Last name is mandatory")
    private String lastName;
    @NotEmpty(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String username;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$", message = "Password must contain at least one uppercase letter, one lowercase letter, and one digit")
    private String password;
    @Positive(message = "Phone Number is mandatory")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must contain exactly 10 digits")
    private String phoneNumber;

    @NotEmpty(message = "Role is mandatory")
    private String role;

}
