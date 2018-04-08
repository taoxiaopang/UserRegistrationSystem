package com.fullstack.spring.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fullstack.spring.demo.dto.UserInfo;
import com.fullstack.spring.demo.repository.UserInfoJpaRepository;

@Service
public class UserInfoDetailsService implements UserDetailsService {
    
    private UserInfoJpaRepository userInfoJpaRepository;

    @Autowired
    void setUserInfoRepository(UserInfoJpaRepository userInfoJpaRepository) {
        this.userInfoJpaRepository = userInfoJpaRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) {
        UserInfo userInfo = userInfoJpaRepository.findByUserName(userName);
        if(userInfo == null) {
            throw new UsernameNotFoundException("Not found with user name: " + userName);
        }
        
        return new User(userInfo.getUserName(), userInfo.getPassword(), AuthorityUtils.createAuthorityList(userInfo.getRole()));
    }
    
}
