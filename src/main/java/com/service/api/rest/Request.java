package com.service.api.rest;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.service.api.json.RequestDeserializer;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * 
 * @author Olga Kholkovskaia 
 */
//@Data
//@Entity
@JsonDeserialize(using = RequestDeserializer.class)
public class Request implements Serializable {
//
////    @EmbeddedId
    private Long id;
   
    private final double time;
    
    private  String xSecret;
    
    private final Map<String, Double> origin;
    
    private final Map<String, Double> destination;
    
    private final List<String> waypointNames;
    
    private final List<Map<String, Double>> waypoints;

    public Request(double time, Map<String, Double> origin, Map<String, Double> destination, 
         List<String> waypointNames, List<Map<String, Double>> waypoints) {
        this.time = time;
        this.origin = origin;
        this.destination = destination;
        this.waypoints = waypoints;
        this.waypointNames = waypointNames;
     }
          

    @Override
    public String toString() {
        return "Request{" + "origin=" + origin + ", destination=" + destination + ", time=" + time + ", waypoints=" + waypoints + '}';
    }

    public List<Map<String, Double>> getWaypoints() {
        return waypoints;
    }

    public double getTime() {
        return time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getxSecret() {
        return xSecret;
    }

    public void setxSecret(String xSecret) {
        this.xSecret = xSecret;
    }
      
    private String pointToString(Map<String, Double> point){
        return String.format("%s,%s", point.get("lon"), point.get("lat"));
    }
    
    /**
     * Name-to-url map for requests.
     * @param prefix   
     * @param postfix   
     * @return
     */
    public Map<String, String> getOSMRequestUrls(String prefix, String postfix){
               
        Map<String, String> urls = new HashMap<>();
        
        for (int i = 0; i < waypoints.size(); i ++){
           Map<String, Double> point = waypoints.get(i);
           StringBuilder sb = new StringBuilder(prefix);
           sb.append(pointToString(origin)).append(";");
           sb.append(pointToString(point)).append(";");
           sb.append(pointToString(this.destination)).append("?");
           sb.append(postfix);
           urls.put(waypointNames.get(i), sb.toString());
        }
        return urls;
    }

    /**
     * Destination coordinates (lon, lat) as doubles.
     * @return
     */
    public double[] getDestination() {
        return new double[]{destination.get("lon"), destination.get("lat")};
    }
  
    public double[] getOrigin() {
        return new double[]{origin.get("lon"), origin.get("lat")};
    }
}
