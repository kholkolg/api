/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.entities;

import com.service.api.distance.DistanceProvider;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 *
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 */

public class Car {
     
    @Autowired
    @Qualifier("haversineDistanceProvider")
    private DistanceProvider dp;
       
    private final String routeName;
    
    private final List<Step> steps;    
    
    private int currentStepIndex; 
    
    private int currentWpIndex;
    
    private double[] currentPosition;
    
    private double[] destination;
    
    private double[] origin;
    
    
    public Car(Route route, DistanceProvider dp){
        
        this.dp = dp;
        
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
    
    
    public double movefromStart(double duration){
        
        reset();
        double routeTime = 0;
        //find the last step
        routeTime = findStep(routeTime, duration);
        Step lastStep = steps.get(currentStepIndex);
        //find last waypoint
        routeTime = findWaypoint(lastStep, routeTime, duration);
        currentPosition = findPointByTime(lastStep, routeTime, duration);
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
    
    
    public double computeDelay(double targetDistance){
       
        double time = 0;
        boolean stop = false;
       
        while(!stop && currentStepIndex < steps.size()){ 
            Step step = steps.get(currentStepIndex);
            double[][] waypoints = step.getWaypoints();
            while(currentWpIndex < waypoints.length){
                double distFromNextStep = dp.getDistanceMeters(destination, waypoints[currentWpIndex]);
                if(Math.abs(distFromNextStep - targetDistance) > 5){
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
        }else{
            point2 = steps.get(currentStepIndex+1).getWaypoints()[0];
        }
        double dist = dp.getDistanceFromPoint1(point1, point2, destination, targetDistance);
        time = dist/ steps.get(currentStepIndex).getSpeedMs();      
        return time;
    }

    
    private void reset(){
        currentStepIndex = 0;
        currentWpIndex = 0;
        currentPosition = origin;
    }
    
}
