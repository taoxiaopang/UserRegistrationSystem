package com.fullstack.spring.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fullstack.spring.demo.dto.UserDTO;

public interface UserJpaRepository extends JpaRepository<UserDTO, Long> {

    Optional<UserDTO> findById(Long id);
    
    UserDTO findByName(String name);
}
