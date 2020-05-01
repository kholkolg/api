/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


/**
 *
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 */

public class Step {

    @JsonProperty("duration")
    private double duration;
    
    @JsonProperty("distance")
    private double distance;
    
    @JsonProperty("waypoints")
    @JsonDeserialize 
    private double[][] waypoints;
            
    public double[] getStartPoint(){
       return waypoints[0];
    }
       
    public double[] getEndPoint(){
        return waypoints[waypoints.length - 1];
        
    }
    
    public double getDuration() {
        return duration;
    }
    public double[] computeCoordinate(double travelTime){
        double r = travelTime / this.duration;
        double[] endPoint = getEndPoint();
        double[] startPoint = getStartPoint();
        double dX = endPoint[0] - startPoint[0];
        double dY = endPoint[1] - startPoint[1];
        double[] newPoint = {startPoint[0] + dX*r, startPoint[1] + dY*r};
        return newPoint;
    }
}
