/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.entities;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 */
public class Route {
    
    private String name;
    
    private final double duration;
    
    private final double distance;
    
    private final List<Step> steps;
    
   
    private Route(double duration, double distance, List<Step> steps) {
        this.distance = distance;
        this.duration = duration;
        this.steps = steps;

    }
    
    @JsonCreator
    public static Route fromJson(String requestStr){
        ObjectMapper mapper = new ObjectMapper();
        Route route = null;
        try {
            JsonNode root = mapper.readTree(requestStr);
            double dist = root.get("distance").asDouble();
            double dur = root.get("duration").asDouble();
            List<Step> steps = Arrays.asList(mapper.readValue(requestStr, Step[].class));
            route =  new Route(dist, dur, steps);
            
        } catch (JsonProcessingException ex) {
            Logger.getLogger(Route.class.getName()).log(Level.SEVERE, null, ex);
        }
        return route;       
        
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
        
    @Override
    public String toString() {
        return "Route{" + "duration=" + duration + ", distance=" + distance + ", steps=" + getNumSteps() + '}';
    }
            
    private int getNumSteps(){
        return steps.size();
    }


    
}
