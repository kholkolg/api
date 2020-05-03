/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 */

public class MockRepositoryImpl<Long, T> implements MockRepository<Long, T> {
    
    private final Map<Long, T> entities = new HashMap<>();
    
    
    @Override
    public void save(Long id, T entity) {
        entities.put(id, entity);
    }

    @Override
    public T findById(Long id) {
        return entities.get(id);
    }

    @Override
    public List<T> findAll() {
        return entities.values().stream().collect(Collectors.toList());
    }
}

