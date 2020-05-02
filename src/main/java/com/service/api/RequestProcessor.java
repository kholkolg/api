/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api;

import com.service.api.routing.FIlERouteProvider;
import com.service.api.routing.RouteProvider;
import com.service.api.distance.DistanceProvider;
import com.service.api.distance.Proj4jDistanceProvider;
import com.service.api.entities.Car;
import com.service.api.entities.Request;
import com.service.api.entities.Response;
import com.service.api.entities.Route;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 */
public class RequestProcessor {
        private final DistanceProvider dp;
        private final RouteProvider rp ;
    
    public RequestProcessor(DistanceProvider dp, RouteProvider rp){
        this.dp = (dp == null) ? new Proj4jDistanceProvider() : dp;
        this.rp = (rp == null) ?  new FIlERouteProvider("") : rp;
    }
    
    
    public Response processRequest(Request request){
        
        // find routes
        List<Route> routes = rp.getRoutes(request);
        List<Car> cars = new ArrayList<>();
        
        //find winner and it's distance to the destination
        double bestDist = Double.MAX_VALUE;
        String bestRoute = "";
        for(Route r : routes){
            Car car = new Car(r, dp);
            cars.add(car);
            double dist = car.movefromStart(request.getTime());
            if(dist < bestDist){
                bestDist = dist;
                bestRoute = car.getRouteName();
            }
        }
        
        // compute delays, save to response
        Response response = new Response();
        for(Car car: cars){
            double delay = car.getRouteName().equals(bestRoute) ? 0 : car.computeDelay(bestDist);
            response.addDelay(car.getRouteName(), delay);
        }
        return response;

    }
    
}
