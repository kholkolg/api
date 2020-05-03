/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.api.entities.Response;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 */
@RestController
public class ResponseController {
    
    @PostMapping("/")
	public Response request(String contents){
        ObjectMapper mapper = new ObjectMapper();
        Response response = null;
        try {
            response = mapper.convertValue(mapper.readTree(contents), Response.class);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(ResponseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }
}
