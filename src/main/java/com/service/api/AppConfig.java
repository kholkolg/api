package com.service.api;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.service.api.bestRoute.RequestProcessor;
import com.service.api.model.distance.DistanceProvider;
import com.service.api.model.distance.Proj4jDistanceProvider;
import com.service.api.rest.request.BestRouteRequestValidator;
import com.service.api.routing.OSMRouteProvider;
import com.service.api.routing.RouteProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 

/**
 *
 * @author Olga Kholkovskaia 
 */
//@Configuration
//public class AppConfig {
//    
//
//    @Bean
//    public DistanceProvider distanceProvider(){
//        return new Proj4jDistanceProvider();
//    }
//    
//    @Bean
//    public RouteProvider routeProvider(){
//        return new OSMRouteProvider();
//    }
//    
//    @Bean
//    public RequestProcessor requestProcessor(){
//        RequestProcessor rp =  new RequestProcessor();
//        rp.setEpsilon(5);
//        return rp;
//    }
//    
//    @Bean
//    public BestRouteRequestValidator bestRouteRequestValidator (){
//        return new BestRouteRequestValidator();
//    }
//    
//   
//}
