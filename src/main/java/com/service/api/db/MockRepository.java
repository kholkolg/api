/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.db;

import java.util.List;

/**
 *
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 */
public interface MockRepository<Long, T> {
    
    public void save(Long id, T entity);
    
    public T findById(Long id);   
    
    public List<T> findAll();
}
