package com.fullstack.spring.demo.rest;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fullstack.spring.demo.dto.UserDTO;
import com.fullstack.spring.demo.exception.UserErrorType;
import com.fullstack.spring.demo.repository.UserJpaRepository;

@RestController
@RequestMapping("/api/user")
public class UserRegistrationRestController {
    private static final Logger logger = LogManager.getLogger(UserRegistrationRestController.class);
    private UserJpaRepository userJpaRepository;

    @Autowired
    public void setUserJpaRepository(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }
    
    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> listAllUsers() {
    
        logger.info("Fetching all users");
        List<UserDTO> users = userJpaRepository.findAll();
        
        if (users.isEmpty()) {    
            return new ResponseEntity<List<UserDTO>>(HttpStatus.NO_CONTENT);   
        }
        
        return new ResponseEntity<List<UserDTO>>(users, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") final Long id) {
    
        logger.info("Fetching User with id {}", id);
        Optional<UserDTO> user = userJpaRepository.findById(id);
        
        if (!user.isPresent()) {
            logger.info("User with id {} not found.", id);
            return new ResponseEntity<UserDTO>(new UserErrorType("User with id " + id + " not found"),
            HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<UserDTO>(user.get(), HttpStatus.OK);

    }
    
    /**
     * @exception MethodArgumentNotValidException
     *                (validation fails)
     */
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody final UserDTO user) {
        logger.info("Creating User : {}", user);
        if(userJpaRepository.findByName(user.getName()) != null) {
            
            logger.info("Unable to create. A User with name {} already exist", user.getName());
            
            return new ResponseEntity<UserDTO>(
                    new UserErrorType(
                            "Unable to create new user. A User with name " + user.getName() + " already exist."),
                    HttpStatus.CONFLICT);
        }
        
        userJpaRepository.save(user);
        
        return new ResponseEntity<UserDTO>(user, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") final Long id, @RequestBody UserDTO user) {
        
        logger.info("Updating User with id {}", id);
        
        Optional<UserDTO> checkUser = userJpaRepository.findById(id);
        if(!checkUser.isPresent()) {
            logger.error("Unable to update. User with id {} not found.", id);
            return new ResponseEntity<UserDTO>(
                    new UserErrorType("Unable to upate. User with id " + id + " not found."), HttpStatus.NOT_FOUND);
        }
        
        UserDTO currentUser = checkUser.get();
        currentUser.setName(user.getName());
        currentUser.setAddress(user.getAddress());
        currentUser.setEmail(user.getEmail());
        userJpaRepository.saveAndFlush(currentUser);
        
        return new ResponseEntity<UserDTO>(currentUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable("id") final Long id) {
        
        logger.info("Deleting User with id {}", id);
        
        Optional<UserDTO> user = userJpaRepository.findById(id);
        if(!user.isPresent()) {
            logger.error("Unable to delete. User with id {} not found.", id);
            return new ResponseEntity<UserDTO>(
                    new UserErrorType("Unable to delete. User with id " + id + " not found."), HttpStatus.NOT_FOUND);
        }

        userJpaRepository.deleteById(id);
        
        return new ResponseEntity<UserDTO>(new UserErrorType("Deleted User with id " + id + "."),
                HttpStatus.NO_CONTENT);
    }

}
