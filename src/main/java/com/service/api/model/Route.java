/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.model;


import com.service.api.json.RouteDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;


/**
 * Route returned by OSMR.
 * With steps from both legs joined to one list.
 * 
 * @author Olga Kholkovskaia
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
     /**
      * Name of the waypoint from the request.
      * @return 
      */
    public String getName() {
        return name;
    }

    public double getDuration() {
        return duration;
    }

    public double getDistance() {
        return distance;
    }

    public void setName(String name) {
        this.name = name;
    }
    /**
     * Last waypoint coordinates.
     * @return 
     */
    public double[] getDestination(){
        return steps.get(steps.size()-1).getEndPoint();
    }
    /**
     * First waypoint coordinates.
     * @return 
     */
    public double[] getOrigin(){
        return this.steps.get(0).getStartPoint();
    }
        
    @Override
    public String toString() {
        return "Route{" + "duration=" + duration + ", distance=" + distance + ", steps=" + getNumSteps() + '}';
    }
            
    private int getNumSteps(){
        return steps.size();
    }
   
}
