/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.distance;

import org.springframework.stereotype.Service;

/**
 *
 * @author Olga Kholkovskaia
 */
//@Service("haversineDistanceProvider")
public class HaversineDistanceProvider implements DistanceProvider{
    final private static int R = 6371000; 
    
    @Override
    public double getDistanceMeters(double[] origin, double[] destination) {

        double lon1 = origin[0];
        double lat1 = origin[1];
        double lon2 = destination[0];
        double lat2 = destination[1];
        
        double latDistance = toRad(lat2-lat1)/2;
        double lonDistance = toRad(lon2-lon1)/2;
        double a = Math.pow(Math.sin(latDistance), 2) + 
        Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) * Math.pow(Math.sin(lonDistance), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distance = R * c;
       
        return distance;
    }
    private  double toRad(double value) {
        return value * Math.PI / 180;
}
    
    
    
}
