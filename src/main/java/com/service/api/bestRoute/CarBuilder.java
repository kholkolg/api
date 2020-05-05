/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.bestRoute;

import com.service.api.model.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 */
@Component
public class CarBuilder {
    
    @Autowired
    private ApplicationContext context;
    
    
    public Car getCar(Route route){
        Car car = context.getBean(Car.class);
        car.setRoute(route);
        return car;
    }
    
}
