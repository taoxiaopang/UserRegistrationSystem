package com.fullstack.spring.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {
    
    //@Autowired
    //private UserInfoDetailsService userInfoDetailsService;

    //@Override
    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //auth.userDetailsService(userInfoDetailsService);
        auth.inMemoryAuthentication()
        .withUser("user").password("password").roles("USER");
        auth.inMemoryAuthentication()
        .withUser("admin").password("password").roles("USER", "ADMIN");

    }
    
    
    //https://www.harinathk.com/spring/no-passwordencoder-mapped-id-null/
    @SuppressWarnings("deprecation")
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().realmName("User Registration System")
        .and().authorizeRequests()
        .antMatchers(HttpMethod.GET, "/api/user/").hasRole("USER")
        .antMatchers(HttpMethod.POST, "/api/user/").hasRole("USER")
        .antMatchers(HttpMethod.PUT, "/api/user/**").hasRole("USER")
        .antMatchers(HttpMethod.DELETE, "/api/user/**").hasRole("ADMIN")
        .and().csrf().disable();
        
        //https://stackoverflow.com/questions/45382328/connecting-to-h2-database-from-h2-console
        http.headers().frameOptions().disable();
    }
    

}
