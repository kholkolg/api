/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.service.api.rest.request.BestRouteRequest;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;



/**
 * Deserializer for BestRouteRequest.
 * All exceptions are handled in BadRequestHandler.
 * 
 * @author Olga Kholkovskaia 
 */

public class RequestDeserializer extends JsonDeserializer<BestRouteRequest>  {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
 
    @Override
    public BestRouteRequest deserialize(JsonParser jp, DeserializationContext ctxt)   {
        
        JsonNode rootNode;
        try {
            rootNode = jp.getCodec().readTree(jp);

            final double time = rootNode.get("time").asDouble();
            final JsonNode originNode = rootNode.get("origin");
            final JsonNode destinationNode = rootNode.get("destination");

            final JsonNode waypointNodes = rootNode.get("waypoints");

            final Map<String, Double> origin = pointNode2map(originNode);
            final Map<String, Double> destination =  pointNode2map(destinationNode);
            
            final List<String> waypointNames = new LinkedList<>();
            final List<Map<String, Double>> waypoints = new LinkedList<>();     
            if (waypointNodes.isArray()) {
                for (final JsonNode wpNode : waypointNodes) {
                    final Map<String, Double> wp = pointNode2map(wpNode);
                    waypointNames.add(wpNode.get("name").asText());
                    waypoints.add(wp);
                }
            return new BestRouteRequest(time, origin, destination, waypointNames, waypoints);
            }
        } catch (Exception ex) {
            LOGGER.severe(ex.getMessage());
        }
        return null;
    }
    
    private Map<String, Double> pointNode2map(JsonNode pointNode){
        Map<String, Double> point = new HashMap<>();
         point.put("lat",  pointNode.get("lat").asDouble());
         point.put("lon",  pointNode.get("lon").asDouble());
         return point;
    }
}

