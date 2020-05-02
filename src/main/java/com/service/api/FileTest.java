/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.api.distance.DistanceProvider;
import com.service.api.distance.Proj4jDistanceProvider;
import com.service.api.entities.Car;
import com.service.api.entities.Request;
import com.service.api.entities.Response;
import com.service.api.entities.Route;
import static com.service.api.json.DeserializationTest.createTestRequest;
import static com.service.api.json.DeserializationTest.readRoutesFile;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 */
public class FileTest {
    
    
    public static void main(String[] args) {
        
        double time = 180;
        DistanceProvider dp = new Proj4jDistanceProvider();
        Request request = createTestRequest();
        if(request == null){
            System.exit(-1);
        }
        List<Route> routes = readRoutesFile(request);
        List<Car> cars = new ArrayList<>();
        double bestDist = Double.MAX_VALUE;
        String bestRoute = "";
        for(Route r : routes){
            Car car = new Car(r, dp);
            cars.add(car);
            double dist = car.movefromStart(time);
            if(dist < bestDist){
                bestDist = dist;
                bestRoute = car.getRouteName();
            }
        }
        System.out.println("Winner :" + bestRoute + ", dist " + bestDist);
        Response response = new Response();
        for(Car car: cars){
            
            System.out.println("Car "+car.getRouteName());
            if(car.getRouteName().equals(bestRoute)){
                response.addDelay(car.getRouteName(), 0);
                continue;
            }
            double delay = car.moveToDist(bestDist);
            response.addDelay(car.getRouteName(), delay);
            System.out.println("route: "+car.getRouteName() + " , delay: "+delay);
        }
        
        ObjectMapper mapper = new ObjectMapper();
        try {
            //Object to JSON in String
            String jsonString = mapper.writeValueAsString(response);
            System.out.println("JsonRequest \n"+jsonString);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(FileTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
	}

}
