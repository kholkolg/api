/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.errorHandlers;

import com.service.api.rest.FailedResponse;
import com.service.api.rest.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 *
 * @author Olga Kholkovskaia 
 */
@ControllerAdvice
public class UserNotFoundErrorHandler {

	@ResponseBody
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Response employeeNotFoundHandler(UserNotFoundException ex) {
		return new FailedResponse("Unathorized request. "+ex.getMessage());
	}

}
