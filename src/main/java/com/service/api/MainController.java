package com.service.api;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.service.api.bestRoute.RequestProcessor;
import com.service.api.db.MockRepository;
import com.service.api.db.MockRepositoryImpl;
import com.service.api.model.distance.Proj4jDistanceProvider;
import com.service.api.rest.FailedResponse;
import com.service.api.rest.Request;
import com.service.api.rest.Response;
import com.service.api.routing.OSMRouteProvider;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * Rest controller for requests.
 * @author Olga Kholkovskaia
 */

@RestController
public class MainController {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    private final RequestProcessor processor = new RequestProcessor(
        new Proj4jDistanceProvider(), new OSMRouteProvider(""), 5);
    
    //Database
    private final AtomicLong idGenerator = new AtomicLong(0L);
    
    private final MockRepository<Long, Request> requests = new MockRepositoryImpl<>();
    
    private final MockRepository<Long, Response> responses = new MockRepositoryImpl<>();
    

	@PostMapping("/api")
	public Response newRequest(@RequestBody Request request){
        LOGGER.info(request.toString());
   
        if(request.getxSecret() == null || !request.getxSecret().equals("Mileus")){
            return new FailedResponse("Anauthorized request.");
        }
        requests.save(idGenerator.getAndIncrement(), request);
        Response response = processor.processRequest(request);
        LOGGER.info(response.toString());
        responses.save(request.getId(), response);
        return response;
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




  
        


