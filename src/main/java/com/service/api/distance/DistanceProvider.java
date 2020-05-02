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
//@Service
public interface DistanceProvider {
    
    /**
     * Returns distance in meters between two given points.
     * @param point1
     * @param pointe
     * @return 
     */
    public double getDistanceMeters(double[] point1, double[] pointe);
    
    /**
     * Returns point lying the line between two points n meters from start.  
     * 
     * @param point1
     * @param point2
     * @param distFromPoint1
     * @return 
     */
    public double[] getPoint(double[] point1, double[] point2, double distFromPoint1);
    
    /**
     * Returns distance from point1 to the point lying the line between two points n meters from destination.  
     * @param point1
     * @param point2
     * @param destination
     * @param distToDestination
     * @return 
     */
     public double getDistanceFromPoint1(double[] point1, double[] point2, double[] destination, double distToDestination);
    
}
