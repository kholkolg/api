/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.model.distance;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.osgeo.proj4j.BasicCoordinateTransform;
import org.osgeo.proj4j.CRSFactory;
import org.osgeo.proj4j.CoordinateReferenceSystem;
import org.osgeo.proj4j.ProjCoordinate;
import org.springframework.stereotype.Component;

/**
 *
 * @author Olga Kholkovskaia
 */
//@Service("Proj4jDistanceProvider")
@Component
public class Proj4jDistanceProvider implements DistanceProvider{
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final BasicCoordinateTransform transformToMetric;
    
    private final BasicCoordinateTransform transformFromMetric;
    
    //
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
        //if waypoints are closer than min distance, stop at the first waypoint
        if(tooClose(dist)){
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
        //if waypoints are closer than min distance, stop at the first waypoint
        if(tooClose(point1Projected, point2Projected)){
            return 0;
        }
        
        double[] destinationProjected = projectPoint(destination, true);
        
        //a^2 = b^2 + c^2 - 2*b*c*cos(alpha)
        // cosine 
        double angle = 1;
        try{
            angle = findAngle(destinationProjected, point2Projected, point1Projected);
        }catch(Exception ex){
                LOGGER.log(Level.SEVERE, 
                 String.format("{0}.Input: p1, p2, dest ({1}, {2}, {3}), projected ({4}, {5}, {6})\n"
                      + "; target point to des {7}",
                  ex.getMessage(), Arrays.toString(point1), Arrays.toString(point2), Arrays.toString(destination),
                  Arrays.toString(point1Projected), Arrays.toString(point2Projected), Arrays.toString(destinationProjected),
                  distToDestination));
        }
        // destination lies on the line between p1 and p2
        if(Math.abs(angle -1) < 0.0001){
            return distToDestination;
        }
        double p1ToDestLen = getDistance(destinationProjected, point1Projected);
        
        // equation coefficients
        double b = -2*p1ToDestLen*angle;
        double c = distToDestination*distToDestination - p1ToDestLen*p1ToDestLen;
        // try to find distance from the start to the target point
        double solution = 0;
        try{
            solution = solve(b,c);
        }catch(Exception ex){
              LOGGER.log(Level.SEVERE, 
                  String.format("{0}.Input: p1, p2, dest ({1}, {2}, {3}), projected ({4}, {5}, {6})\n"
                      + "; target point to des {7}, p1 to dest {8}; cos(alpha) {9}; b {10}, c {11}",
                  ex.getMessage(), Arrays.toString(point1), Arrays.toString(point2), Arrays.toString(destination),
                  Arrays.toString(point1Projected), Arrays.toString(point2Projected), Arrays.toString(destinationProjected),
                  distToDestination, p1ToDestLen, angle, b, c ));
              
        }
        return solution;
    }
    
    private double findAngle(double[] point1, double[] point2, double[] commonPoint){
        //TODO some vector library
        double[] vec1 = new double[]{point1[0] - commonPoint[0], point1[1] - commonPoint[1]};
        double[] vec2 = new double[]{point2[0] - commonPoint[0], point2[1] - commonPoint[1]};
        double vec1Norm = getDistance(point1, commonPoint);
        double vec2Norm = getDistance(point2, commonPoint);
        double crossProd = vec1[0]*vec2[0] + vec1[1]*vec2[0];
        double angle = crossProd/(vec1Norm*vec1Norm*vec2Norm*vec2Norm);
        
        return angle;
    }
    
    private double solve(double b, double c){
        double desc = Math.sqrt(1 - 4*b*c);
        double sol = (-b + desc)/(2);
        return sol;
    }
    
    private boolean tooClose(double dist){
        return dist < minDist;
    }
    
    private boolean tooClose(double[] point1, double[] point2){
        return tooClose(getDistance(point1, point2));
    }
}
