/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.entities;

import java.util.List;

/**
 *
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 */
public class Car {
        
    private final String routeName;
    
    private final List<Step> steps;    
    
    private int currentStepIndex; 
        
    public Car(Route route){
        this.routeName = route.getName();
        
        this.currentStepIndex = 0;
        
        this.steps = route.getSteps();
    }
    
    public double movefromStart(double duration){
        currentStepIndex = 0;
        double routeTime = 0;
        double stepDuration = steps.get(currentStepIndex).getDuration();
        
        while((routeTime + stepDuration) < duration && currentStepIndex < steps.size()){
            routeTime += stepDuration;
            currentStepIndex++;
            stepDuration = steps.get(currentStepIndex).getDuration();
        }
        Step targetStep = steps.get(currentStepIndex);
        double timeLeft = duration - routeTime;
        double[] lastWaypoint = targetStep.computeCoordinate(timeLeft);
        
        //TODO distance here or
        return 0;
    }
    
    // TODO compute final result
    public double moveToDist(double distance){
        double[] destination = steps.get(steps.size() - 1).getEndPoint();
        
        
        return 0;
    }
    
    public String getRouteName(){
        return this.routeName;
    }
    
}
