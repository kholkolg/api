/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.db;

/**
 *
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 */

public class UserRepository<Long, T> extends MockRepositoryImpl<Long, T> {
    
    public String getxSecret(Long id) {
        return "Mileus";
    }
}

