package com.epam.everest.LocalGoods.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Table(name = "forget_password")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForgetPasswordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long Id;
    @Column(name = "OTP")
    private Integer otp;
    @Column(name = "ExpiryTime")
    private Date expiryTime;
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
}
