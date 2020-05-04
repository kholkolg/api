/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.errorHandlers;


import com.service.api.rest.FailedResponse;
import com.service.api.rest.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ServerErrorHandler {

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(java.net.UnknownHostException.class)
    @ResponseBody
    public Response handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return new FailedResponse("Connection to osmr server failed. "+ ex.getMessage());
    }


}

