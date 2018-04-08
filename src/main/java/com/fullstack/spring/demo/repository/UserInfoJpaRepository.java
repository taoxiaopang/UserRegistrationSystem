package com.fullstack.spring.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fullstack.spring.demo.dto.UserInfo;

public interface UserInfoJpaRepository extends JpaRepository<UserInfo, Long> {
  
    UserInfo findByUserName(String userName);
}
