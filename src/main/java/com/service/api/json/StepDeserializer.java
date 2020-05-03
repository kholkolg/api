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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.api.model.Step;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Deserializer for steps.
 * @author Olga Kholkovskaia 
 */

public class StepDeserializer extends JsonDeserializer<Step> {

    @Override
    public Step deserialize(JsonParser jp, DeserializationContext ctxt) {
        
        try {
            ObjectMapper mapper = new ObjectMapper();
            
            final JsonNode rootNode = jp.getCodec().readTree(jp);
            
            final Double distance = rootNode.get("distance").asDouble();
            
            final Double duration = rootNode.get("duration").asDouble();
            
            final JsonNode wpNodes = rootNode.get("geometry").get("coordinates");
            
            double[][] waypoints = mapper.convertValue(wpNodes, double[][].class);
            
            return new Step(duration, distance, waypoints);
        } catch (Exception ex) {
            Logger.getLogger(StepDeserializer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
  
}

