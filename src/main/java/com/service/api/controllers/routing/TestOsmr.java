/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.controllers.routing;

import com.service.api.entities.Request;
import com.service.api.entities.Route;
import static com.service.api.json.TestJson.createTestRequest;
import java.util.List;

/**
 *
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 */
public class TestOsmr {
    

    public static void main(String[] args) {
            
        // Read request from json       
        Request request = createTestRequest();
        OSMRouteProvider rp = new OSMRouteProvider("");
        System.out.println("Request:\n" +request);
//      List<Route> routes = osmr.getRoutes(request);
        if(request == null){
            System.exit(-1);
        }
        List<Route> routes = rp.getRoutes(request);
        for(Route r : routes){
            if(r != null){
                System.out.println(r);
            }else{
                System.out.println("no route returned");
            }
        }
    }

}
