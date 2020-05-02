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
    
    private final BasicCoordinateTransform transformToMetric;
    
    private final BasicCoordinateTransform transformFromMetric;
    
    public Proj4jDistanceProvider(){
        
        CRSFactory factory = new CRSFactory();
        CoordinateReferenceSystem srcCrs = factory.createFromName("EPSG:4326");
        CoordinateReferenceSystem dstCrs = factory.createFromName("EPSG:3310");
        transformToMetric = new BasicCoordinateTransform(srcCrs, dstCrs);
        transformFromMetric = new BasicCoordinateTransform(dstCrs, srcCrs);
}
    
    @Override
    public double getDistanceMeters(double[] origin, double[] destination) {
  
        double[] originProjected = projectPoint(origin, true);
        double[] destProjected = projectPoint(destination, true);
      
        double dist = getDistance(originProjected, destProjected);
        return dist;   
    }
    
    @Override
    public double[] getPoint(double[] origin, double[] destination, double distFromOrigin) {
        
        double[] originProjected = projectPoint(origin, true);
        double[] destProjected = projectPoint(destination, true);
        
        double dist = getDistance(originProjected, destProjected);
        double alpha = distFromOrigin/dist;
        double dX = destProjected[0] - originProjected[0];
        double dY = destProjected[1] - originProjected[1];
        double[] pointProjected = new double[]{originProjected[0] + alpha*dX, originProjected[1] + alpha * dY};
        double[] point = projectPoint(pointProjected, false);
        return point;
    }
        
    private double[] projectPoint(double[] point, boolean toMetric){
        ProjCoordinate srcCoord = new ProjCoordinate(point[0], point[1]);
        ProjCoordinate destCoord = new ProjCoordinate();
        if(toMetric){
            transformToMetric.transform(srcCoord, destCoord);
        }else{
            transformFromMetric.transform(srcCoord, destCoord);
        }
        return new double[]{destCoord.x, destCoord.y};
    }
    
   
    private double getDistance(double[] origin, double[] dest){
        return Math.sqrt(Math.pow(dest[0] - origin[0], 2) + Math.pow(dest[1] - origin[1], 2));
    }
   
}
