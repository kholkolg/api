/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api;
import static com.service.api.json.TestJson.createTestRequest;
import com.service.api.rest.Request;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
//
///**
// *
// * @author Olga Kholkovskaia
// */

//@Configuration
//@Slf4j
public class TestApi {
    static String DIR = "/home/olga/NetBeansProjects/api/data/";

     public static void main(String[] args) {
        String curl = "curl -X POST localhost:8080/api -H 'Content-type:application/json' -d '";
         List<String> requests = prepareRequest();
         for(String r : requests){
            StringBuilder sb = new StringBuilder(curl);
            sb.append(r).append("'");
            System.out.println(sb.toString());
         }
      
      }
    

//public class RequestGenerator {
    
//	@Bean
//	CommandLineRunner postRequest() {
//        List<String> requests = prepareRequest();
//		return args -> {
////			log.info("Preloading " + repository.save(new Request("Bilbo Baggins", "burglar")));
////			log.info("Preloading " + repository.save(new Request("Frodo Baggins", "thief")));
//		};
//	}
     
    
    
    private static List<String> prepareRequest(){
        String[] points =  new String[]{"\"lat\": 50.023226,\"lon\": 14.439855",
                                        "\"lat\": 50.121765629793295,\"lon\": 14.489431312606477",
                                        "\"lat\": 50.058010,\"lon\": 14.406775",
                                        "\"lat\": 50.060757,\"lon\": 14.431909",
                                        "\"lat\": 50.078847,\"lon\": 14.538084"};
       
        String good = "{\"x-secret\":\"Mileus\",";
        String bad = "{\"x-secret\":\"text\",";
        
        List<String> requests = new ArrayList<>();
        
        String r =  addPayload(good, points[0], points[1], 
            new String[]{points[2], points[3], points[4]}, 180);
        requests.add(r);
                
        String r1 = addPayload(bad, points[0], points[1], 
            new String[]{points[2], points[3], points[4]}, 180);
         requests.add(r1);
        
        String r2 = addPayload(good, points[1],  points[0],
            new String[]{points[2], points[3]}, 120);
        requests.add(r2);
        
        String r3 = addPayload(bad, points[1],  points[0],
            new String[]{points[2],  points[4]}, 120);
        requests.add(r3);
        
        return requests;
    }
    
    
    private static String addPayload(String xSecret, String origin, String dest, String[] waypoints, double time){
        StringBuilder sb = new StringBuilder(xSecret);
        sb.append("\"origin\":{").append(origin).append("},\"destination\":{").append(dest);
        sb.append("},\"time\":").append(time).append(",\"waypoints\":[");
        for(int i = 0; i < waypoints.length; i++){
            sb.append("{\"name\":\"Point ").append(i).append('"').append(",").append(waypoints[i]).append("}");
            if(i != waypoints.length-1)    sb.append(",");
        }
        sb.append("]}");
        return sb.toString();
    }
}
////curl -X POST localhost:8080/api -H 'Content-type:application/json' -d '{"origin": {"lat": 50.023226, "lon": 14.439855},"destination":{"lat": 50.121765629793295,"lon": 14.489431312606477},"time": 180,"waypoints": [{"name": "Point A","lat": 50.058010,"lon": 14.406775 },{ "name": "Point B", "lat": 50.060757, "lon": 14.431909},{"name": "Point C", "lat": 50.078847, "lon": 14.538084}]}'
//
//
////}
