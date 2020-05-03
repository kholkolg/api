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
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 */
//@Data
//@Entity
@JsonDeserialize(using = RequestDeserializer.class)
public class Request implements Serializable {
//
////    @EmbeddedId
    private Long id;
   
//    @JsonProperty("time")
    private double time;
    
//    @JsonProperty("x-secret")
    private String xSecret;
    
//    @JsonProperty("origin")
//    @JsonDeserialize 
    private Map<String, String> origin;
    
//    @JsonProperty("destination")
//    @JsonDeserialize 
    private Map<String, String> destination;
 
//    @JsonProperty("waypoints")
//    @JsonDeserialize 
    private List<Map<String, String>> waypoints;

    public Request(double time, Map<String, String> origin, Map<String, String> destination, 
        List<Map<String, String>> waypoints, String xSecret) {
        this.time = time;
        this.origin = origin;
        this.destination = destination;
        this.waypoints = waypoints;
        this.xSecret = xSecret;
    }
          

    @Override
    public String toString() {
        return "Request{" + "origin=" + origin + ", destination=" + destination + ", time=" + time + ", waypoints=" + waypoints + '}';
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

      
    private String pointToString(Map<String, String> point){
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
        
        for (Map<String, String> point : this.waypoints){
           StringBuilder sb = new StringBuilder(prefix);
           sb.append(pointToString(origin)).append(";");
           sb.append(pointToString(point)).append(";");
           sb.append(pointToString(this.destination)).append("?");
           sb.append(postfix);
           urls.put(point.get("name"), sb.toString());
        }
        return urls;
    }

    /**
     * Destination coordinates (lon, lat) as doubles.
     * @return
     */
    public double[] getDestination() {
        return new double[]{Double.parseDouble(destination.get("lon")), Double.parseDouble(destination.get("lat"))};
    }
  

}
