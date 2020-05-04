/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.db;

import java.util.List;

/**
 * Mock repository.
 * @author Olga Kholkovskaia 
 * @param <N> entity's id type
 * @param <T> entity type
 */
public interface MockRepository<N, T> {
    
    public void save(N id, T entity);
    
    public T findById(N id);   
    
    public List<T> findAll();
}
