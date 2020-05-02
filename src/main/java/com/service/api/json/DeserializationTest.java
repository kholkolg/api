/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.api.ApiApplication;
import com.service.api.OSMR;
import com.service.api.entities.Request;
import com.service.api.entities.Route;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author Olga Kholkovskaia 
 */
public class DeserializationTest {
        
    static String DIR = "/home/olga/NetBeansProjects/api/data/";
/**
     * Read json request from the file
     * @param filename
     * @return
     */
    public static String readRequesFile(String filename){
        
        StringBuilder sb = new StringBuilder("{\"x-secret\":\"Mileus\",");
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            
            br.lines().collect(Collectors.toList()).forEach((l) -> {
                sb.append(l);
            });
          } catch (IOException ex) {
            Logger.getLogger(ApiApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        sb.append("}");
        return sb.toString();
    }
    
     public static Request createTestRequest(){
          // Read request from json       
        String inputFile = DIR + "request.json";
        String requestStr = readRequesFile(inputFile);
        Request request = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            request = mapper.readValue(requestStr,  Request.class);
           
        } catch (IOException ex) {
            Logger.getLogger(ApiApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return request;
     }
    
    
    public static List<Route> readRoutesFile(Request request){
        
        String[] files = {"Point A", "Point B", "Point C"};  
        ObjectMapper mapper = new ObjectMapper();
        List<Route> routes = new ArrayList<>();
        Route route = null; 
        Map<String, String> urlsMap = request.getOSMRequestUrls("http://router.project-osrm.org/route/v1/driving/");
        for(Map.Entry<String,String> e : urlsMap.entrySet()){
            StringBuilder sb = new StringBuilder();
            try(BufferedReader br = new BufferedReader(new FileReader(String.format("%s%s.json", DIR, e.getKey())))){
                br.lines().collect(Collectors.toList()).forEach((l) -> { sb.append(l);});
                String result = sb.toString();
                JsonNode resultObj =  mapper.readTree(result);
                JsonNode routeObj = resultObj.get("routes").get(0);
                route = mapper.convertValue(routeObj, Route.class);
                route.setName(e.getKey());
                System.out.println("New route: " +route);
                routes.add(route);
            }catch (IOException ex) {
                Logger.getLogger(OSMR.class.getName()).log(Level.SEVERE, null, ex);
            }             
        }
        return routes;   
    }
    
    
      public static void main(String[] args) throws IOException, URISyntaxException {
		
        // Read request from json       
        Request request = createTestRequest();
        System.out.println("Request:\n" +request);
//      List<Route> routes = osmr.getRoutes(request);
        if(request != null){
            List<Route> routes = readRoutesFile(request);
           for(Route r : routes) System.out.println(r);
        }
           
        
	}

}
