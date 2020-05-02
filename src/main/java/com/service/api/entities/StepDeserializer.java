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

public class StepDeserializer extends JsonDeserializer<Step> {

    @Override
    public Step deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        
        ObjectMapper mapper = new ObjectMapper();
        final JsonNode rootNode = jp.getCodec().readTree(jp);
        
        final Double distance = rootNode.get("distance").asDouble();
        final Double duration = rootNode.get("duration").asDouble();
        final List<double[]> waypoints = new ArrayList<>();
        
        final JsonNode wpNodes = rootNode.get("geometry").get("coordinates");
        if (wpNodes.isArray()) {
            for (final JsonNode wpNode : wpNodes) {
//                System.out.println(wpNode);
            if(wpNode.isArray()){
                double[] wp = new double[]{wpNode.get(0).asDouble(), wpNode.get(1).asDouble()};
                waypoints.add(wp);
                }
            }
        }
        double[][] wps = waypoints.stream().toArray(double[][]::new);
    return new Step(duration, distance, wps);
    }
  
}

