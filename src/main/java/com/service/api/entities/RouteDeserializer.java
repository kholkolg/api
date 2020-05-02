/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.entities;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 */

public class RouteDeserializer extends JsonDeserializer<Route> {
    
      
    @Override
    public Route deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        
        ObjectMapper mapper = new ObjectMapper();
        final JsonNode rootNode = jp.getCodec().readTree(jp);
        
        final Double distance = rootNode.get("distance").asDouble();
        final Double duration = rootNode.get("duration").asDouble();
        final List<Step> steps = new ArrayList<>();
        
        final JsonNode legNodes = rootNode.get("legs");
        if (legNodes.isArray()) {
            for (final JsonNode legNode : legNodes) {
       //         System.out.println(legNode);
                final JsonNode stepNodes = legNode.get("steps");
                if(stepNodes.isArray()){
                    for(final JsonNode stepNode: stepNodes){
                        Step step = mapper.convertValue(stepNode, Step.class);
                        steps.add(step);
                    }
                }
            }
        }
    return new Route(duration, distance, steps);
  }
}

