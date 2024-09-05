package com.epam.everest.LocalGoods.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Table(name = "otp")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtpEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long Id;
    @Column(name = "OTP")
    private Integer otp;
    @Column(name = "ExpiryTime")
    private Date expiryTime;
    @Column(name = "email")
    private String username;
}
