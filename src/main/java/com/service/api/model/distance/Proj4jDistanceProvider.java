/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.model.distance;

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
    
    private final double minDist;
    
    public Proj4jDistanceProvider(){
        this.minDist = 1;
        
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
        if(dist < minDist){
            return origin;
        }
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

    @Override
    public double getDistanceFromPoint1(double[] point1, double[] point2, double[] destination, double distToDestination) {
        double[] point1Projected = projectPoint(point1, true);
        double[] point2Projected = projectPoint(point2, true);
        double[] destinationProjected = projectPoint(destination, true);
        
        // cosine 
        double angle = findAngle(destinationProjected, point2Projected, point1Projected);
        double p1ToDestLen = getDistance(destinationProjected, point1Projected);
        
        // equation coefficients
        double a = 1;
        double b = -2*p1ToDestLen*angle;
        double c = distToDestination*distToDestination - p1ToDestLen*p1ToDestLen;
        
        // 
        double solution = solve(a,b,c);

        return solution;
    }
    
    private double findAngle(double[] point1, double[] point2, double[] commonPoint){
        
        double[] vec1 = new double[]{point1[0] - commonPoint[0], point1[1] - commonPoint[1]};
        double[] vec2 = new double[]{point2[0] - commonPoint[0], point2[1] - commonPoint[1]};
        double vec1Norm = getDistance(point1, commonPoint);
        double vec2Norm = getDistance(point2, commonPoint);
        double crossProd = vec1[0]*vec2[0] + vec1[1]*vec2[0];
        double angle = crossProd/(vec1Norm*vec1Norm*vec2Norm*vec2Norm);
        
        return angle;
    }
    
    private double solve(double a, double b, double c){
        double desc = Math.sqrt(a*a - 4*b*c);
        double sol = (-b + desc)/(2*a);
//        double sol2 = (-b -desc)/(2*a);
//        double sol = sol1 >= 0 ? sol1 : sol2;
        return sol;
     }
   
}
