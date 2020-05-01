/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.entities;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.service.api.entities.Request;
import java.io.IOException;


/**
 *
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 */

public class RequestDeserializer extends JsonDeserializer<Request> {

  @Override
   public Request deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

    JsonNode root = jp.getCodec().readTree(jp);
    JsonNode origin = root.get("origin");
    JsonNode dest = root.get("destination");
    JsonNode waypoints = root.get("waypoints");

//    final String name = node.get("name").asText();
//    final String contents = node.get("contents").asText();
//    final long ownerId = node.get("ownerId").asLong();
//    User user = new User();
//    user.setId(ownerId);
    return new Request();

  }

}

