package com.fullstack.spring.demo.exception;

import com.fullstack.spring.demo.dto.UserDTO;

public class UserErrorType extends UserDTO {

	private String errorMessage;
	 
    public UserErrorType(final String errorMessage){
        this.errorMessage = errorMessage;
    }
 
    public String getErrorMessage() {
        return errorMessage;
    }
}
