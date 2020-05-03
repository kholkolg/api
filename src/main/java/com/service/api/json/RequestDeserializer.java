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
import com.service.api.rest.Request;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * Deserializer for Request
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 */

public class RequestDeserializer extends JsonDeserializer<Request> {
    
      
    @Override
    public Request deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        
        final JsonNode rootNode = jp.getCodec().readTree(jp);
        
        final String xSecret = rootNode.get("x-secret").asText();
        final double time = rootNode.get("time").asDouble();
        
        final JsonNode originNode = rootNode.get("origin");
        final JsonNode destinationNode = rootNode.get("destination");
        final JsonNode waypointNodes = rootNode.get("waypoints");
        
        final Map<String, String> origin = pointNode2map(originNode);
        final Map<String, String> destination =  pointNode2map(destinationNode);
     
        List<Map<String, String>> waypoints = new LinkedList<>();     
        if (waypointNodes.isArray()) {
            for (final JsonNode wpNode : waypointNodes) {
                Map<String, String> wp = pointNode2map(wpNode);
                waypoints.add(wp);
            }
        }
        
    return new Request(time, origin, destination, waypoints, xSecret);
  }
    
    private Map<String, String> pointNode2map(JsonNode pointNode){
        Map<String, String> point = new HashMap<>();
         point.put("lat",  pointNode.get("lat").asText());
         point.put("lon",  pointNode.get("lon").asText());
         if(pointNode.get("name") != null){
             point.put("name",  pointNode.get("name").asText());
         }
         return point;
    }
}

