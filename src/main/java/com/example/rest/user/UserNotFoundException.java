package com.example.rest.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// HTTP Status Code
// 2XX -> OK
// 4XX -> Client 
// 5XX -> Server 에러
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
	
	public UserNotFoundException(String message) {
		super(message);
	}
}
