/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.api.model.Route;
import com.service.api.model.Step;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Deserializer for Route.
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 */

public class RouteDeserializer extends JsonDeserializer<Route> {
    
      
    @Override
    public Route deserialize(JsonParser jp, DeserializationContext ctxt) {
        
        try {
            ObjectMapper mapper = new ObjectMapper();
            
            final JsonNode rootNode = jp.getCodec().readTree(jp);
            
            final Double distance = rootNode.get("distance").asDouble();
            
            final Double duration = rootNode.get("duration").asDouble();
            
            final List<Step> steps = new LinkedList<>();
            
            final JsonNode legNodes = rootNode.get("legs");
            
            if (legNodes.isArray()) {
                
                for (final JsonNode legNode : legNodes) {
                    
                    final JsonNode stepNodes = legNode.get("steps");
                    
                    List<Step> legSteps = Arrays.asList(mapper.convertValue(stepNodes, Step[].class));
                    
                    steps.addAll(legSteps);
                }
            }
            
            return new Route(duration, distance, steps);
        } catch (Exception ex) {
            Logger.getLogger(RouteDeserializer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
  }
}

