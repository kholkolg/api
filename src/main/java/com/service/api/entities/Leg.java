/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 */
public class Leg {

//    private List<Step> steps;
//        
//    private Leg(List<Step> steps){
//        this.steps = steps;
//    }
//        
//    @JsonCreator
//    public static Leg fromJson(JsonNode root){
//        ObjectMapper mapper = new ObjectMapper();
//        Leg leg = null;
//        JsonNode stepsNode = root.get("steps");
//        try {
//            List<Step> steps =  Arrays.asList(mapper.readValue(stepsNode.asText(), Step[].class));
//            leg = new Leg(steps);
//        } catch (JsonProcessingException ex) {
//            Logger.getLogger(Route.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        return leg;
//    }
}
