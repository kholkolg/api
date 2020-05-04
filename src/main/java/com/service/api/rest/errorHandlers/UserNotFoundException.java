/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.errorHandlers;

/**
 *
 * @author Olga Kholkovskaia 
 */
public class UserNotFoundException extends RuntimeException{

	public UserNotFoundException(Long id) {
		super("User " + id);
	}
}

