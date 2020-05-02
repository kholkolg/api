package com.service.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;




@SpringBootApplication
public class ApiApplication {
    
    
    static String DIR = "/home/olga/NetBeansProjects/api/data/";
/**
     * Read json request from the file
     * @param filename
     * @return
     */
    public static String createTestRequest(String filename){
        
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
//                String routeStr = routeObj.asText();
                route = mapper.convertValue(routeObj, Route.class);
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
        String inputFile = DIR + "request.json";
        String requestStr = createTestRequest(inputFile);
        Request request = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            request = mapper.readValue(requestStr,  Request.class);
           
        } catch (IOException ex) {
            Logger.getLogger(ApiApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        OSMR osmr = new OSMR();
        System.out.println("Request:\n" +request);
        
//        List<Route> routes = osmr.getRoutes(request);
         List<Route> routes = readRoutesFile(request);
        for(Route r : routes) System.out.println(r);
           
        SpringApplication.run(ApiApplication.class, args);
        
	}

}
