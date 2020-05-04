/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.model;

import com.service.api.bestRoute.Car;
import com.service.api.rest.Request;
import com.service.api.model.distance.DistanceProvider;
import com.service.api.model.distance.Proj4jDistanceProvider;
import static com.service.api.json.Main.createTestRequest;
import static com.service.api.json.Main.readRoutesFile;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Olga Kholkovskaia 
 */

public class Main {
 
       
	public static void main(String[] args) {
        
        DistanceProvider dp = new Proj4jDistanceProvider();
        Request request = createTestRequest();
        System.out.println("Request:\n" +request);
        if(request == null){
            System.exit(-1);
        }
        
        List<Route> routes = readRoutesFile(request);
        if(routes == null){
            System.exit(-1);
        }
        for(Route r : routes)    System.out.println(r);
       
        double time = 180;
        List<Car> cars = new ArrayList<>();
        double bestDist = Double.MAX_VALUE;
        String bestRoute = "";
        for(Route r : routes){
            Car car = new Car(r, dp);
            cars.add(car);
            double dist = car.movefromStart(time);
            System.out.println("Car " + car.getRouteName() + ", dist " + dist);
            if(dist < bestDist){
                bestDist = dist;
                bestRoute = car.getRouteName();
            }
        }
        System.out.println("Winner :" + bestRoute + ", dist " + bestDist);
        for(Car car: cars){
            System.out.println("Car "+car.getRouteName());
            if(car.getRouteName().equals(bestRoute)){
                continue;
            }
            double delay = car.computeDelay(bestDist, 5);
            System.out.println("route: "+car.getRouteName() + " , delay: "+delay);
        }
	}

    
}
