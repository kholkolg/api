/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.rest.validator;

import com.service.api.rest.Request;


/**
 *
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 * @param <T>
 */
public class RequestValidator<T> implements DataValidator<T> {
       
    private final static double[] longitudeLimits = new double[]{10, 20};
    
    private final static double[] latitudeLimits = new double[]{45, 55};
          
     
    @Override
    public boolean isValid(T data){
        if(data == null){
            return false;
        }
        if(data.getClass() != Request.class){
            return false;
        }
        Request request = (Request) data;
        if(request.getDestination() == null || request.getDestination().length != 2){
            return false;
        }
        if(request.getOrigin() == null || request.getOrigin().length != 2){
            return false;
        }
        
        
        return true;
        
    }
   
    private boolean checkCoordinates(double[] coordinates){
        return true;
    }
    
}
