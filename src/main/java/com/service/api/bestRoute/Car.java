/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.bestRoute;

import com.service.api.model.Route;
import com.service.api.model.Step;
import com.service.api.model.distance.DistanceProvider;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author Olga Kholkovskaia 
 */
@Service
@Scope("prototype")
public class Car {
    
    @Autowired
    @Qualifier("proj4jDistanceProvider")
    private DistanceProvider dp;
       
    private  String routeName;
    
    private List<Step> steps;    
    
    private double[] currentPosition;
    
    private double[] destination;
    
    private double[] origin;
    
    private int currentStepIndex; 
    
    private int currentWpIndex;
    
    
    protected void setRoute(Route route){
         this.routeName = route.getName();
        
        this.destination = route.getDestination();
        
        this.currentStepIndex = 0;
        
        this.currentWpIndex = 0;
        
        this.origin = route.getOrigin();
        
        this.currentPosition =  route.getOrigin();
        
        this.steps = route.getSteps();
    }
    
    public String getRouteName(){
        return routeName;
    }
    
    /**
     * Computes distance from destination after the given time;
     * @param timeToTravel
     * @return 
     */
    
    public double movefromStart(double timeToTravel){
        
        reset();
        double routeTime = 0;
        //find the last step
        routeTime = findStep(routeTime, timeToTravel);
        Step lastStep = steps.get(currentStepIndex);
        //find last waypoint
        routeTime = findWaypoint(lastStep, routeTime, timeToTravel);
        currentPosition = findPointByTime(lastStep, routeTime, timeToTravel);
        double dist = dp.getDistanceMeters(currentPosition, destination);
        return dist;
    }
    
    private double findStep(double routeTime, double duration){
         double stepDuration = steps.get(currentStepIndex).getDuration();
        
        //find the last step
        while((routeTime + stepDuration) < duration && currentStepIndex < steps.size()){
            routeTime += stepDuration;
            currentStepIndex++;
            stepDuration = steps.get(currentStepIndex).getDuration();
        }
        return routeTime;
    }
    /**
     * Returns the time the car will move along the route
     * until it's distance to the destination point becomes 
     * almost equal to target distance of the winner.
     * 
     * @param targetDistance
     * @return 
     */
    public double computeDelay(double targetDistance, double epsilon){
       
        double time = 0;
        boolean stop = false;
       
        while(!stop && currentStepIndex < steps.size()){ 
            Step step = steps.get(currentStepIndex);
            double[][] waypoints = step.getWaypoints();
            while(currentWpIndex < waypoints.length){
                double distFromNextStep = dp.getDistanceMeters(destination, waypoints[currentWpIndex]);
                if(Math.abs(distFromNextStep - targetDistance) > epsilon){
                    stop = true;
                    break;
                }else{
                    double stepDistance = dp.getDistanceMeters(currentPosition, waypoints[currentWpIndex]);
                    time += stepDistance / step.getSpeedMs();
                    currentPosition = waypoints[currentWpIndex];
                    currentWpIndex++;
                }
            }
            if(stop){
                break;
            }else{
                currentStepIndex++;
                currentWpIndex = 0;
            }
       }
        double[][] waypoints = steps.get(currentStepIndex).getWaypoints();
        double[] point1 = waypoints[currentWpIndex];
        double[] point2 = new double[2];
        if(currentWpIndex < waypoints.length-1){
             point2 = waypoints[currentWpIndex + 1];
        }else if(currentStepIndex < steps.size()){
            point2 = steps.get(currentStepIndex+1).getWaypoints()[0];
        }else{
            point2 = point1;
        }
        double dist = dp.getDistanceFromPoint1(point1, point2, destination, targetDistance);
        time = dist/ steps.get(currentStepIndex).getSpeedMs();      
        return time;
    }

    
    private double findWaypoint(Step step, double routeTime, double duration){
        double[][] waypoints = step.getWaypoints();
        double speed = step.getSpeedMs();
        double stepDistance = dp.getDistanceMeters(waypoints[currentWpIndex], waypoints[currentWpIndex + 1]);
        double stepDuration = stepDistance / speed;
        while((routeTime + stepDuration) < duration && currentWpIndex < waypoints.length - 1){
            routeTime += stepDuration;
            currentWpIndex++;
            stepDistance = dp.getDistanceMeters(waypoints[currentWpIndex], waypoints[currentWpIndex + 1]);
            stepDuration = stepDistance / speed;
        }
        return routeTime;
    }
    
    private double[] findPointByTime(Step step, double routeTime, double duration){
        double[][] waypoints = step.getWaypoints();
        double timeLeft = duration - routeTime;
        double distFromLastWp = timeLeft * step.getSpeedMs();
        double[] endPoint = dp.getPoint(waypoints[currentWpIndex], waypoints[currentStepIndex+1], distFromLastWp);
        return endPoint;
    }
       
    
    private void reset(){
        currentStepIndex = 0;
        currentWpIndex = 0;
        currentPosition = origin;
    }
    
}
