/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.routing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.api.rest.request.BestRouteRequest;
import com.service.api.model.Route;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;


/**
 *
 * @author Olga Kholkovskaia 
 */
@Component("fileRouteProvider")
public class FIlERouteProvider implements RouteProvider {
   final String dir= "/home/olga/NetBeansProjects/api/data/";
    

    /**
     * BestRouteRequest to routes.
     * 
     * @param request .
     * @return
     */
   @Override
    public List<Route> getRoutes(BestRouteRequest request){
       
        List<Route> routes = new ArrayList<>();
        Route route = null; 
        Map<String, String> urlsMap = request.getOSMRequestUrls("", "");
        for(Map.Entry<String,String> e : urlsMap.entrySet()){
            StringBuilder sb = new StringBuilder();
            try(BufferedReader br = new BufferedReader(new FileReader(String.format("%s%s.json", dir, e.getKey())))){
                br.lines().collect(Collectors.toList()).forEach((l) -> { sb.append(l);});
                String result = sb.toString();
                route = getRoute(result);
                route.setName(e.getKey());
                System.out.println("New route: " +route);
                routes.add(route);
            }catch (IOException ex) {
                Logger.getLogger(FIlERouteProvider.class.getName()).log(Level.SEVERE, null, ex);
            }             
        }
        return routes;   
       
    }
           

    private Route getRoute(String result){
        ObjectMapper mapper = new ObjectMapper();
        Route route = null; 
        try {
            JsonNode root =  mapper.readTree(result);
            JsonNode routeObj = root.get("routes").get(0);
            route = mapper.convertValue(routeObj, Route.class);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(FIlERouteProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        return route;        
    }
   
}

