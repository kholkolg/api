/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.entities;

import com.service.api.ApiApplication;
import com.service.api.distance.DistanceProvider;
import com.service.api.distance.HaversineDistanceProvider;
import com.service.api.distance.Proj4jDistanceProvider;
import static com.service.api.json.DeserializationTest.createTestRequest;
import static com.service.api.json.DeserializationTest.readRoutesFile;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
/**
 *
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 */

public class TestCarMovement {
//       @Autowired
//       @Qualifier("haversineDistanceProvider")
 
       
	public static void main(String[] args) {
        
        DistanceProvider dp = new Proj4jDistanceProvider();
        Request request = createTestRequest();
        System.out.println("Request:\n" +request);
        if(request == null){
            System.exit(-1);
        }
        List<Route> routes = readRoutesFile(request);
        for(Route r : routes)    System.out.println(r);
       
        double time = 180;
        List<Car> cars = new ArrayList<>();
        double bestDist = Double.MAX_VALUE;
        String bestRoute = "";
        for(Route r : routes){
            Car car = new Car(r, dp);
            double dist = car.movefromStart(time);
            System.out.println("Car " + car.getRouteName() + ", dist " + dist);
            if(dist < bestDist){
                bestDist = dist;
                bestRoute = car.getRouteName();
            }
        }
        System.out.println("Winner :" + bestRoute + ", dist " + bestDist);
	}

    
}
