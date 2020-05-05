/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.rest.request;


import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;


/**
 * Checks time and coordinate ranges.
 * Coordinate limits set for Prague region.
 * 
 * @author Olga Kholkovskaia 
 * 
 */
@Component
public class BestRouteRequestValidator {
       
    private final double[] longitudeLimits = new double[]{10, 20};
    
    private final  double[] latitudeLimits = new double[]{45, 55};
          
     /**
      * Check request parameters.
      * Time should be positive, coordinates inside the valid ranges for the given region.
      * 
      * @param request
      * @return 
      */
    public boolean isValid(BestRouteRequest request){
        if(request == null){
            return false;
        }
        if(request.getTime() < 0){
            return false;
        }
        //check origin and destination coordinates
        if(request.getDestination() == null || request.getDestination().length != 2){
            return false;
        }
        if(request.getOrigin() == null || request.getOrigin().length != 2){
            return false;
        }
        if(!areCoordinatesValid(request.getOrigin()) || !areCoordinatesValid(request.getDestination())){
            return false;
        }
        //check waypoints
        List<Map<String, Double>> wps = request.getWaypoints();
        if(wps == null || wps.isEmpty()){
            return false;
        }
        if(!wps.stream().noneMatch((wp) -> (!areCoordinatesValid(wp.get("lon"), wp.get("lat"))))) {
            return false;
//        for(Map<String, Double> wp: wps){
//            System.out.println(wp.entrySet());
//            double lon = wp.get("lon");
//            double lat = wp.get("lat");
//            if(!areCoordinatesValid(lon, lat)){
//                return false;
//            }
        }
    return true;
        
    }
   
    private boolean areCoordinatesValid(double lon, double lat){
//        double minLon = longitudeLimits[0];
//        double maxLon = longitudeLimits[1];
//        double minLat = latitudeLimits[0];
//        double maxLat = latitudeLimits[1];
//        if(lon < minLon){
//            return false;
//        }
//        if(lon > maxLon){
//            return false;
//        }
//        if(lat < minLat){
//            return false;
//        }
//        if(lat > maxLat){
//            return false;
//        }
//        return true;
        return !(lon < longitudeLimits[0] || lon > longitudeLimits[1] ||
            lat < latitudeLimits[0] || lat > latitudeLimits[1]);
    
    }
    
    private boolean areCoordinatesValid(double[] coordinates){
        return areCoordinatesValid(coordinates[0],  coordinates[1]);
    }
    
}
