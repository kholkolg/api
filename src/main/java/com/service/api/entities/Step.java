/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 */
@JsonDeserialize(using = StepDeserializer.class)
public class Step {

    private double duration;
    
    private double distance;
    
    private double[][] waypoints;
    
    public Step(double duration, double distance, double[][] waypoints){
        this.duration = duration;
        this.distance = distance;
        this.waypoints = waypoints;        
    }
    
//    @JsonCreator
//    public static Step fromJson(String requestStr){
//        
//        ObjectMapper mapper = new ObjectMapper();
//        Step step = null;
//        try {
//            JsonNode root = mapper.readTree(requestStr);
//            double dist = root.get("distance").asDouble();
//            double dur = root.get("duration").asDouble();
//            JsonNode coords = root.get("geometry").get("coordinates");
//            double[][] waypoints = mapper.readValue(coords.asText(), double[][].class);
//            step =  new Step(dist, dur, waypoints);
//            
//        } catch (JsonProcessingException ex) {
//            Logger.getLogger(Route.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return step;
//    }
    
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
