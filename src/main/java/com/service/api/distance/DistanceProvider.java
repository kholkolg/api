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
    
    public double getDistanceMeters(double[] origin, double[] destination);
    
    public double[] getPoint(double[] origin, double[] destination, double distFromOrigin);
    
}
