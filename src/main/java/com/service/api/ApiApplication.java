package com.service.api;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.api.entities.Request;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;




@SpringBootApplication
public class ApiApplication {
/**
     * Read json request from the file
     * @param filename
     * @return
     */
    public static String createTestRequest(String filename){
        
        StringBuilder sb = new StringBuilder("{\"x-secret\":\"Mileus\",");
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            
            br.lines().collect(Collectors.toList()).forEach((l) -> {
                sb.append(l);
            });
          } catch (IOException ex) {
            Logger.getLogger(ApiApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        sb.append("}");
        return sb.toString();
    }

    public static void main(String[] args) {
		String DIR = "/home/olga/NetBeansProjects/api/data/";
               // Read request from json       
        String inputFile = DIR + "request.json";
        String requestStr = createTestRequest(inputFile);
        try {
            ObjectMapper mapper = new ObjectMapper();
            Request request = mapper.readValue(requestStr,  Request.class);
            System.out.println("Request:\n" +request);
        } catch (IOException ex) {
            Logger.getLogger(ApiApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
           
        SpringApplication.run(ApiApplication.class, args);
        
	}

}
