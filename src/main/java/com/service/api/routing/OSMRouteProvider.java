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

import java.io.IOException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;




/**
 *
 * @author Olga Kholkovskaia 
 */
@Component("osmRouteProvider")
public class OSMRouteProvider implements RouteProvider {
    private final  String url =  "http://router.project-osrm.org/route/v1/driving/";
    
    private final String osmParams = "geometries=geojson&overview=false&steps=true";
    private final HttpClient httpClient = HttpClients.createDefault();

   
    /**
     * Sends request to OSM routing service..
     * 
     * @param request .
     * @return routes returned by OSM
     */
    @Override
    public List<Route> getRoutes(BestRouteRequest request){
        List<Route> routes = new ArrayList<>();
         Map<String, String> urlsMap = request.getOSMRequestUrls(url, osmParams);
        for(Map.Entry<String,String> e : urlsMap.entrySet()){
            Route route = getRoute(e.getKey(), e.getValue());
            if(route != null){
                routes.add(route);
            }
        }  
        return routes;
    }
  
    
    private JsonNode getOSMResponse(String url)  {
        ObjectMapper mapper = new ObjectMapper();
		JsonNode result = null;
        //TODO timeout
		try {
			URIBuilder builder = new URIBuilder(url);
			URI uri = builder.build();
			HttpGet request = new HttpGet(uri);
			request.addHeader("accept", "application/json");
			HttpResponse response = httpClient.execute(request);
			result = mapper.readTree(response.getEntity().getContent());
           
        } catch (NumberFormatException | UnsupportedOperationException | URISyntaxException | IOException ex){
			 Logger.getLogger(OSMRouteProvider.class.getName()).log(Level.SEVERE, null, ex);
		}
        return result;
    }
    

    private Route getRoute(String name, String url){
        ObjectMapper mapper = new ObjectMapper();
        Route route = null;
        try{
            JsonNode result = getOSMResponse(url);
            String code = result.get("code").asText().toLowerCase();
            if(!code.equals("ok")){
                Logger.getLogger(OSMRouteProvider.class.getName()).log(Level.SEVERE, null, code);
                return null;
            }
            JsonNode routeObj = result.get("routes").get(0);
            route = mapper.convertValue(routeObj, Route.class);
            route.setName(name);
        }catch(Exception ex){
            Logger.getLogger(OSMRouteProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
 
        return route;        
    }
   
}

