/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 */
//@Data
//@Entity
public class Response implements Serializable {
    
  
//    @EmbeddedId
//    private Long id;
//    
//    private Long requestId;
    
    @JsonProperty("winnerName")
    private String winnerName;
    
    @JsonProperty("delays")
    @JsonSerialize 
    private Map<String, Double> delays;
    
    
    public Response(){
        this.delays = new HashMap<>();
    }
   
   public void addDelay(String name, double delay){
       delays.put(name, delay);
       if(delay == 0){
           winnerName = name;
       }
   }
   @JsonIgnore
   public boolean isValid(){
        return !(winnerName == null || winnerName.equals("") || delays.isEmpty());
   }
   
    @Override
    public String toString() {
        StringBuilder sb  = new StringBuilder("Response{");
        sb.append("winnerName:").append(winnerName).append(", delays{");
        for(Map.Entry<String, Double> e : delays.entrySet()){
            sb.append(e.getKey()).append(":").append(e.getValue()).append(", ");
        }
        sb.append("}}");
        
        return sb.toString();
    }
}
