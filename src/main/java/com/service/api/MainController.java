package com.service.api;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.api.db.MockRepository;
import com.service.api.db.MockRepositoryImpl;
import com.service.api.model.distance.Proj4jDistanceProvider;
import com.service.api.rest.Request;
import com.service.api.rest.Response;
import com.service.api.routing.OSMRouteProvider;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
/**
 *
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 */
@Slf4j
@RestController
public class MainController {
    private final AtomicLong idGenerator = new AtomicLong(0L);
    private final MockRepository<Long, Request> requests = new MockRepositoryImpl<>();
    private final MockRepository<Long, Response> responses = new MockRepositoryImpl<>();
    private final RequestProcessor processor = new RequestProcessor(
        new Proj4jDistanceProvider(), new OSMRouteProvider(""));
    

	@PostMapping("/api")
	public String newRequest(@RequestBody Request request){
        log.debug(request.toString());
        
        if(request.getxSecret() == null || !request.getxSecret().equals("Mileus")){
            log.debug("anauthorized request");
            return "Anauthorized request.";
        }
        Response response = processor.processRequest(request);
        responses.save(request.getId(), response);
        if(!response.isValid()){
            log.debug("bad response "+response);
            return "Invalid response.";
        }
        String responseStr;
        try {
            responseStr = new ObjectMapper().writeValueAsString(response);
        } catch (JsonProcessingException ex) {
            log.error(ex.getMessage());
            return "Invalid response.";
        }
        return responseStr;
    }
        
    @GetMapping("/api/requests")
    public List<Request>  allRequests(){
        return requests.findAll();
    }
    
    @GetMapping("/api/routes{id}")
    public Response  response(@PathVariable Long id){
        return responses.findById(id);
    }
    
}




  
        


