/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.controllers.routing;

import com.service.api.entities.Request;
import com.service.api.entities.Route;
import java.util.List;

/**
 *
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 */
public interface RouteProvider {
    
     public List<Route> getRoutes(Request request);
}
