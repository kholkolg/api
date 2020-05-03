/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.rest.validator;

/**
 *
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 */
public interface DataValidator<T> {
    
    
    public boolean isValid(T data);
    
}
