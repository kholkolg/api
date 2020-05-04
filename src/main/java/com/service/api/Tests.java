/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api;

import com.service.api.bestRoute.RequestProcessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.api.model.distance.DistanceProvider;
import com.service.api.model.distance.Proj4jDistanceProvider;
import com.service.api.bestRoute.Car;
import com.service.api.rest.Request;
import com.service.api.rest.GoodResponse;
import com.service.api.model.Route;
import static com.service.api.json.Main.createTestRequest;
import static com.service.api.json.Main.readRoutesFile;
import com.service.api.rest.Response;
import com.service.api.routing.OSMRouteProvider;
import com.service.api.routing.RouteProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Olga Kholkovskaia 
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
        GoodResponse response = new GoodResponse();
        for(Car car: cars){
            
            System.out.println("Car "+car.getRouteName());
            if(car.getRouteName().equals(bestRoute)){
                response.addDelay(car.getRouteName(), 0);
                continue;
            }
            double delay = car.computeDelay(bestDist, 5);
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
        RequestProcessor processor = new RequestProcessor(null, null, 5);
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
        
        RequestProcessor processor = new RequestProcessor(dp, rp, 5);
        
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
//        test1(request, time);
//        test2(request);
        testOsmr(request);
       
        
	}

}
