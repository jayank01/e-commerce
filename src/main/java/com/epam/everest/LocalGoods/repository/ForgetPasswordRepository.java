package com.epam.everest.LocalGoods.repository;

import com.epam.everest.LocalGoods.entity.ForgetPasswordEntity;
import com.epam.everest.LocalGoods.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ForgetPasswordRepository extends CrudRepository<ForgetPasswordEntity, Long> {
    @Query("SELECT f FROM ForgetPasswordEntity f WHERE f.otp = ?1 AND f.userEntity = ?2")
    Optional<ForgetPasswordEntity> findByOtpAndUserEntity(Integer otp, UserEntity userEntity);
    Optional<ForgetPasswordEntity> findByUserEntity(UserEntity userEntity);
    @Transactional
    void deleteByUserEntity(UserEntity userEntity);
}
