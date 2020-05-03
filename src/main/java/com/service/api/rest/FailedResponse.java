/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.rest;

import java.io.Serializable;


/**
 *
 * @author Olga Kholkovskaia
 */

public class FailedResponse implements Response, Serializable {

    public FailedResponse(String message) {
        this.message = message;
    }
     
    
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
