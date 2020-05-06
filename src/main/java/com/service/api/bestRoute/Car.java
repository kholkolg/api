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
    
    private double duration;
    
    private double distance;
    
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
        
        this.duration = route.getDuration();
        
        this.distance = route.getDistance();
        
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
        if(timeToTravel >= duration){
            currentStepIndex = steps.size()-1;
            currentWpIndex = steps.get(currentStepIndex).getWaypoints().length-1;
            currentPosition = destination;
            return 0.0;
        }
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
     * @param epsilon
     * @return 
     */
    public double computeDelay(double targetDistance, double epsilon){

        if(dp.getDistanceMeters(currentPosition, destination) <= targetDistance){
            return 0.0;
        }
        Step step = steps.get(currentStepIndex);
        double time = 0;
        double currentDistance = dp.getDistanceMeters(destination, step.getWaypoints()[currentWpIndex+1]);
        
        while(currentDistance > targetDistance && currentStepIndex < steps.size()){
            double[][] wps = step.getWaypoints();
            while(currentDistance > targetDistance && currentWpIndex < wps.length-2){
                currentWpIndex++;
                double length = dp.getDistanceMeters(currentPosition, wps[currentWpIndex]);
                double duration = length/step.getSpeedMs();
                time += duration;
                currentPosition = wps[currentWpIndex];
                currentDistance = dp.getDistanceMeters(destination, step.getWaypoints()[currentWpIndex+1]);
            }
            if(currentDistance > targetDistance){
                currentWpIndex++;
                double length = dp.getDistanceMeters(currentPosition, wps[currentWpIndex]);
                double duration = length/step.getSpeedMs();
                currentPosition = wps[currentWpIndex];
                time += duration;
                currentStepIndex++;
                step = steps.get(currentStepIndex);
                currentWpIndex = 0;
                currentDistance = dp.getDistanceMeters(destination, step.getWaypoints()[currentWpIndex]);
                
            }else{
                break;
            }
        }
        double[][] waypoints = step.getWaypoints();
        double[] point = waypoints[currentWpIndex+1];
        double dist = dp.getDistanceFromPoint1(currentPosition, point, destination, targetDistance);
        time += dist/ step.getSpeedMs();      
        return time;
     }

    
    private double findWaypoint(Step step, double routeTime, double duration){
        double[][] waypoints = step.getWaypoints();
        double speed = step.getSpeedMs();
        double stepDistance = dp.getDistanceMeters(waypoints[currentWpIndex], waypoints[currentWpIndex + 1]);
        double stepDuration = stepDistance / speed;
        while((routeTime + stepDuration) < duration && currentWpIndex < (waypoints.length - 2)){
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
        double[] p1 = waypoints[currentWpIndex];
        double[] p2 = waypoints[currentWpIndex+1];
        double[] endPoint = dp.getPoint(p1, p2, distFromLastWp);
        return endPoint;
    }
       
    
    private void reset(){
        currentStepIndex = 0;
        currentWpIndex = 0;
        currentPosition = origin;
    }
    
}
