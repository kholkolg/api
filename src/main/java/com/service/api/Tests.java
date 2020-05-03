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
import static com.service.api.json.TestJson.createTestRequest;
import static com.service.api.json.TestJson.readRoutesFile;
import com.service.api.routing.OSMRouteProvider;
import com.service.api.routing.RouteProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 */
public class Tests {
    
    public static void test1(Request request, double time){
         System.out.println("Test1:");
        DistanceProvider dp = new Proj4jDistanceProvider();
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
            double delay = car.computeDelay(bestDist);
            response.addDelay(car.getRouteName(), delay);
            System.out.println("route: "+car.getRouteName() + " , delay: "+delay);
        }
        
        ObjectMapper mapper = new ObjectMapper();
        try {
            //Object to JSON in String
            String jsonString = mapper.writeValueAsString(response);
            System.out.println("JsonResponse \n"+jsonString);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(Tests.class.getName()).log(Level.SEVERE, null, ex);
        }
 
    }
    
    public static void test2(Request request){
        System.out.println("Test2:");
        RequestProcessor processor = new RequestProcessor(null, null);
        Response response = processor.processRequest(request);
        ObjectMapper mapper = new ObjectMapper();
        try {
            //Object to JSON in String
            String jsonString = mapper.writeValueAsString(response);
            System.out.println("JsonResponse \n"+jsonString);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(Tests.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void testOsmr(Request request){
          System.out.println("TestOsm:");
        DistanceProvider dp = new Proj4jDistanceProvider();
        RouteProvider rp = new OSMRouteProvider("");
        
        RequestProcessor processor = new RequestProcessor(dp, rp);
        
        Response response = processor.processRequest(request);
         try {
            //Object to JSON in String
            String jsonString = new ObjectMapper().writeValueAsString(response);
//          {"winnerName":"Point B","delays":{"Point C":30.841516795702823,"Point B":0.0,"Point A":79.85632108712774}}
            System.out.println("JsonResponse \n"+jsonString);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(Tests.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void main(String[] args) {
                
        DistanceProvider dp = new Proj4jDistanceProvider();
        Request request = createTestRequest();
        if(request == null){
            System.exit(-1);
        }
        double time = 180;
        test1(request, time);
        test2(request);
        testOsmr(request);
       
        
	}

}
