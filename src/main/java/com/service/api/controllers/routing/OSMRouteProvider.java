/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.controllers.routing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.api.entities.Request;
import com.service.api.entities.Route;
import com.service.api.entities.Step;
import java.io.IOException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;




/**
 *
 * @author Olga Kholkovskaia 
 */
public class OSMRouteProvider implements RouteProvider {
    private final  String url;
    
    private final String osmParams = "geometries=geojson&overview=false&steps=true";
    private final HttpClient httpClient = HttpClients.createDefault();

    public OSMRouteProvider(String url){
        if(url.equals("")){
            this.url =  "http://router.project-osrm.org/route/v1/driving/";
        }else{
            this.url = url;
        }
        
    }
   
    /**
     * Request to routes.
     * 
     * @param request .
     * @return
     */
    @Override
    public List<Route> getRoutes(Request request){
        List<Route> routes = new ArrayList<>();
         Map<String, String> urlsMap = request.getOSMRequestUrls(url, osmParams);
        for(Map.Entry<String,String> e : urlsMap.entrySet()){
            Route route = getRoute(e.getKey(), e.getValue());
            routes.add(route);
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
        JsonNode result = getOSMResponse(url);
        String code = result.get("code").asText().toLowerCase();
        if(!code.equals("ok")){
            Logger.getLogger(OSMRouteProvider.class.getName()).log(Level.SEVERE, "osm error {0}", code);
            return null;
        }
        JsonNode routeObj = result.get("routes").get(0);
        Route route = mapper.convertValue(routeObj, Route.class);
        route.setName(name);
 
        return route;        
    }
   
}

