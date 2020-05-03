/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.rest;

import com.service.api.rest.Request;
import java.util.List;
import java.util.Map;


/**
 *Checks time (positive), and coordinate ranges.
 * Coordinate limits set for Prague region.
 * 
 * @author Olga Kholkovskaia 
 * 
 */
public class RequestValidator {
       
    private final static double[] longitudeLimits = new double[]{10, 20};
    
    private final static double[] latitudeLimits = new double[]{45, 55};
          
     
    public boolean isValid(Request request){
        if(request == null){
            return false;
        }
        if(request.getTime() < 0){
            return false;
        }
        if(request.getDestination() == null || request.getDestination().length != 2){
            return false;
        }
        if(request.getOrigin() == null || request.getOrigin().length != 2){
            return false;
        }
        if(!checkCoordinates(request.getOrigin()) || !checkCoordinates(request.getDestination())){
            return false;
        }      
        List<Map<String, Double>> wps = request.getWaypoints();
        if(wps == null || wps.isEmpty()){
            return false;
        }
        for(Map<String, Double> wp :wps){
            if(!checkCoordinates(wp.get("lon"), wp.get("lat"))){
                return false;
            }
        }
    return true;
        
    }
   
     private boolean checkCoordinates(double lon, double lat){
        return !(lon < longitudeLimits[0] || lon > longitudeLimits[1] ||
            lat < latitudeLimits[0] || lat > latitudeLimits[1]);
    
    }
    private boolean checkCoordinates(double[] coordinates){
        return checkCoordinates( coordinates[0],  coordinates[0]);
    }
    
}
