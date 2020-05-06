/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.routing;

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
        ObjectMapper mapper = new ObjectMapper();
        List<Route> routes = new ArrayList<>();
 
        Map<String, String> urlsMap = request.getOSMRequestUrls("", "");
        for(Map.Entry<String,String> e : urlsMap.entrySet()){
            try(BufferedReader br = new BufferedReader(new FileReader(String.format("%s%s.json", dir, e.getKey())))){
                JsonNode routeNode =  mapper.readTree(br).get("routes").get(0);
                Route route = mapper.convertValue(routeNode, Route.class);
                route.setName(e.getKey());
                routes.add(route);
            }catch (IOException ex) {
                Logger.getLogger(FIlERouteProvider.class.getName()).log(Level.SEVERE, null, ex);
            }             
        }
        return routes;   
    }

}

