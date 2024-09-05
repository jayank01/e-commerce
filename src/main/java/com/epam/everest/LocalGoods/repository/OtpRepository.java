package com.epam.everest.LocalGoods.repository;

import com.epam.everest.LocalGoods.entity.OtpEntity;
import com.epam.everest.LocalGoods.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface OtpRepository extends CrudRepository<OtpEntity, Long> {
    @Query("SELECT f FROM OtpEntity f WHERE f.otp = ?1 AND f.username = ?2")
    Optional<OtpEntity> findByOtpAndUsername(Integer otp, String username);
    @Transactional
    void deleteByUsername(String username);
}
