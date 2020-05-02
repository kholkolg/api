/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.distance;

import org.osgeo.proj4j.BasicCoordinateTransform;
import org.osgeo.proj4j.CRSFactory;
import org.osgeo.proj4j.CoordinateReferenceSystem;
import org.osgeo.proj4j.ProjCoordinate;
import org.springframework.stereotype.Service;

/**
 *
 * @author Olga Kholkovskaia
 */
//@Service("Proj4jDistanceProvider")
public class Proj4jDistanceProvider implements DistanceProvider{
    
    private final BasicCoordinateTransform transform;
    
    public Proj4jDistanceProvider(){
        
        CRSFactory factory = new CRSFactory();
        CoordinateReferenceSystem srcCrs = factory.createFromName("EPSG:4326");
        CoordinateReferenceSystem dstCrs = factory.createFromName("EPSG:3310");
        transform = new BasicCoordinateTransform(srcCrs, dstCrs);
}
    
    @Override
    public double getDistanceMeters(double[] origin, double[] destination) {
  
        ProjCoordinate originCoord = new ProjCoordinate(origin[0], origin[1]);
        ProjCoordinate destCoord = new ProjCoordinate(destination[0], destination[1]);
        ProjCoordinate origingProjected = new ProjCoordinate();
        ProjCoordinate destProjected = new ProjCoordinate();
        
        transform.transform(originCoord, origingProjected);
        transform.transform(destCoord, destProjected);
        
        double dist = getDistance(origingProjected, destProjected);
        return dist;   
    }

   
    private double getDistance(ProjCoordinate origin, ProjCoordinate dest){
        return Math.sqrt(Math.pow(dest.x - origin.x, 2) + Math.pow(dest.y - origin.y, 2));
    }
    
    
}
