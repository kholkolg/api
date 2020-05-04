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
 * @author Olga Kholkovskaia
 */

public class MockRepositoryImpl<N, T> implements MockRepository<N, T> {
    
    private final Map<N, T> entities = new HashMap<>();
    
    
    @Override
    public void save(N id, T entity) {
        entities.put(id, entity);
    }

    @Override
    public T findById(N id) {
        return entities.get(id);
    }

    @Override
    public List<T> findAll() {
        return entities.values().stream().collect(Collectors.toList());
    }
}

