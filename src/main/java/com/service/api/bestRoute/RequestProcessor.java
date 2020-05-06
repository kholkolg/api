/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.bestRoute;

import com.service.api.rest.request.BestRouteRequest;
import com.service.api.rest.response.GoodResponse;
import com.service.api.model.Route;
import com.service.api.rest.response.FailedResponse;
import com.service.api.rest.response.Response;
import com.service.api.routing.RouteProvider;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 * @author Olga Kholkovskaia
 */
@Component("bestRouteProcessor")
public class RequestProcessor {
    
    @Autowired
    @Qualifier("fileRouteProvider")
    private RouteProvider routeProvider ;

    @Autowired
    private CarBuilder carBuilder;
    
    private double epsilon = 5;

    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
    }
    
    
    public Response processRequest(BestRouteRequest request){
        // find routes
        List<Route> routes = routeProvider.getRoutes(request);
        if(routes == null || routes.isEmpty()){
            return new FailedResponse("Routes not found");
        }
        List<Car> cars = new ArrayList<>();
        
        //find winner and it's distance to the destination
        double bestDist = Double.MAX_VALUE;
        String bestRoute = "";
        for(Route route : routes){
            Car car = carBuilder.getCar(route);
            cars.add(car);
            double dist = car.movefromStart(request.getTime());
            if(dist < bestDist){
                bestDist = dist;
                bestRoute = car.getRouteName();
            }
        }
        // compute delays, save to response
        GoodResponse response = new GoodResponse();
        for(Car car: cars){
            double delay = car.getRouteName().equals(bestRoute) ? 0 : car.computeDelay(bestDist, epsilon);
            response.addDelay(car.getRouteName(), delay);
        }
        if(!response.isComplete()){
            return new FailedResponse("Incomplete response. " + response);
        }
        return response;
    }
}
