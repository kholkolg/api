/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.entities;

import com.service.api.distance.DistanceProvider;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 */
@Service
public class Car {
     
    @Autowired
    @Qualifier("haversineDistanceProvider")
    private DistanceProvider dp;
       
    private final String routeName;
    
    private final List<Step> steps;    
    
    private int currentStepIndex; 
    
    private int currentWpIndex;
    
    private double[] destination;
        
    public Car(Route route, DistanceProvider dp){
        this.dp = dp;
        this.routeName = route.getName();
        
        this.destination = route.getDestination();
        
        this.currentStepIndex = 0;
        
        this.currentWpIndex = 0;
        
        this.steps = route.getSteps();
    }
    
    public double movefromStart(double duration){
        
        reset();
        double routeTime = 0;
        double stepDuration = steps.get(currentStepIndex).getDuration();
        
        //find the last step
        while((routeTime + stepDuration) < duration && currentStepIndex < steps.size()){
            routeTime += stepDuration;
            currentStepIndex++;
            stepDuration = steps.get(currentStepIndex).getDuration();
        }
        Step lastStep = steps.get(currentStepIndex);
        //find last waypoint
        double[][] waypoints = lastStep.getWaypoints();
        double speed = lastStep.getSpeedMs();
        double stepDistance = dp.getDistanceMeters(waypoints[currentWpIndex], waypoints[currentWpIndex + 1]);
        stepDuration = stepDistance / speed;
        while((routeTime + stepDuration) < duration && currentWpIndex < waypoints.length - 1){
            routeTime += stepDuration;
            currentWpIndex++;
            stepDistance = dp.getDistanceMeters(waypoints[currentWpIndex], waypoints[currentWpIndex + 1]);
            stepDuration = stepDistance / speed;
        }
        double[] lastWaypoint = waypoints[currentWpIndex];
        double timeLeft = duration - routeTime;
        System.out.println(routeName + ", time left " + timeLeft);
        double dist = dp.getDistanceMeters(lastWaypoint, destination);
        return dist;
    }
    
    // TODO compute final result
    public double moveToDist(double distance){
        double[] destination = steps.get(steps.size() - 1).getEndPoint();
        
        
        return 0;
    }
    
    public String getRouteName(){
        return routeName;
    }
    
    private void reset(){
        currentStepIndex = 0;
        currentWpIndex = 0;
    }
    
}
