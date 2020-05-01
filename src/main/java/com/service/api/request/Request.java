package com.service.api.request;


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

//TODO desirializer??
public class Request {
    
    private final static String URL = "http://router.project-osrm.org/route/v1/driving/";
    
    private Map<String, String> origin;
    private Map<String, String> destination;
    private float time;
    
    private Map<String, String>[] waypoints;

    @Override
    public String toString() {
        return "Request{" + "origin=" + origin + ", destination=" + destination + ", time=" + time + ", waypoints=" + waypoints + '}';
    }

      
    private String pointToString(Map<String, String> point){
        return String.format("%s,%s", point.get("lon"), point.get("lat"));
    }
    
    /**
     * Name-to-url map for requests.
     * @return
     */
    public Map<String, String> getOSMRequestUrls(){
               
        Map<String, String> urls = new HashMap<>();
        
        for (Map<String, String> point : this.waypoints){
           StringBuilder sb = new StringBuilder(URL);
           sb.append(pointToString(origin)).append(";");
           sb.append(pointToString(point)).append(";");
           sb.append(pointToString(this.destination)).append("?");
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
