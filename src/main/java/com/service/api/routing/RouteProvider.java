/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.routing;

import com.service.api.rest.Request;
import com.service.api.model.Route;
import java.util.List;

/**
 *Routing service interface
 * 
 * @author Olga Kholkovskaia 
 */
public interface RouteProvider {
    
     public List<Route> getRoutes(Request request);
}
