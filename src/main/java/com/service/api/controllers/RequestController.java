/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.api.entities.Request;
import com.service.api.entities.Response;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 *
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 */
@RestController
public class RequestController {

	@GetMapping("/x-secret")
	public Request request(String contents){
        ObjectMapper mapper = new ObjectMapper();
        Request request = null;
        try {
            request = mapper.convertValue(mapper.readTree(contents), Request.class);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(ResponseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return request;
    }
            
        

    
}
