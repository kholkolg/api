/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.model;


import com.service.api.json.RouteDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;
import org.springframework.context.annotation.Bean;


/**
 *
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 */

@JsonDeserialize(using = RouteDeserializer.class)
public class Route {
    
    private String name;
    
    private final double duration;
    
    private final double distance;
    
    private final List<Step> steps;
        

    public Route(double duration, double distance, List<Step> steps) {
        
        this.distance = distance;
        
        this.duration = duration;
        
        this.steps = steps;
    }
    
    public List<Step> getSteps() {
        return steps;
    }
       
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public double[] getDestination(){
        return this.steps.get(this.steps.size()-1).getEndPoint();
    }
    
    public double[] getOrigin(){
        return this.steps.get(0).getEndPoint();
    }
        
    @Override
    public String toString() {
        return "Route{" + "duration=" + duration + ", distance=" + distance + ", steps=" + getNumSteps() + '}';
    }
            
    private int getNumSteps(){
        return steps.size();
    }
   
}
