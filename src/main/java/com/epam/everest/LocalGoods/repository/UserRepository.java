package com.epam.everest.LocalGoods.repository;

import com.epam.everest.LocalGoods.Handler.UserNotFoundException;
import com.epam.everest.LocalGoods.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
     Optional<UserEntity> findByUsername(String email);
}
