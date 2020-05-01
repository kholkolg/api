package com.service.api.entities;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.ArrayList;
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

public class Request {


   
    @JsonProperty("time")
    private float time;
    
    @JsonProperty("x-secret")
    private String xSecret;
    
    @JsonProperty("origin")
    @JsonDeserialize 
    private Map<String, String> origin;
    
    @JsonProperty("destination")
    @JsonDeserialize 
    private Map<String, String> destination;
 
    @JsonProperty("waypoints")
    @JsonDeserialize 
    private Map<String, String>[] waypoints;
    
//      @JsonCreator
//    public Request(){
//        
//    }
    @Override
    public String toString() {
        return "Request{" + "origin=" + origin + ", destination=" + destination + ", time=" + time + ", waypoints=" + waypoints + '}';
    }

      
    private String pointToString(Map<String, String> point){
        return String.format("%s,%s", point.get("lon"), point.get("lat"));
    }
    
    /**
     * Name-to-url map for requests.
     * @param url url with port
     * @return
     */
    public Map<String, String> getOSMRequestUrls(String url){
               
        Map<String, String> urls = new HashMap<>();
        
        for (Map<String, String> point : this.waypoints){
           StringBuilder sb = new StringBuilder(url);
           sb.append(pointToString(origin)).append(";");
           sb.append(pointToString(point)).append(";");
           sb.append(pointToString(this.destination)).append("?");
           //TODO pass as args
           sb.append("geometries=geojson&overview=false&steps=true");
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
