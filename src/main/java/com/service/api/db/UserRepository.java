/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.db;

import com.service.api.db.errorHandlers.UserNotFoundException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author Olga Kholkovskaia 
 */
@Service("userRepository")
public class UserRepository<N, T> extends MockRepositoryImpl<N, T> {
    
    public String getxSecret(Long id){
        if(id != 0){
            throw new UserNotFoundException(id);
        }
        return "Mileus";
    }
 
    
   
}

