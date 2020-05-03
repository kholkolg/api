/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.model;

import com.service.api.json.StepDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


/**
 * Step of the route with time, distance, and list of coordinate pairs.
 * 
 * @author Olga Kholkovskaia
 */
@JsonDeserialize(using = StepDeserializer.class)
public class Step {

    private final double duration;
    
    private final double distance;
    
    private final double[][] waypoints;
    
    
    public Step(double duration, double distance, double[][] waypoints){
        
        this.duration = duration;
        
        this.distance = distance;
        
        this.waypoints = waypoints;        
    }
    
    
    public double[] getStartPoint(){
       return waypoints[0];
    }
       
    public double[] getEndPoint(){
        return waypoints[waypoints.length - 1];
    }
    
    public double getDuration() {
        return duration;
    }
 
    public double[][] getWaypoints(){
        return waypoints;
    }
    
    public double getSpeedMs(){
        return distance/duration;
    }
     
}
