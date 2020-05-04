package com.service.api;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.service.api.bestRoute.RequestProcessor;
import com.service.api.db.MockRepository;
import com.service.api.db.MockRepositoryImpl;
import com.service.api.db.UserRepository;
import com.service.api.model.distance.Proj4jDistanceProvider;
import com.service.api.rest.response.FailedResponse;
import com.service.api.rest.request.BestRouteRequest;
import com.service.api.rest.request.BestRouteRequestValidator;
import com.service.api.rest.response.Response;
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
    
    private final BestRouteRequestValidator rv = new BestRouteRequestValidator();
    
    //Database
    private final AtomicLong idGenerator = new AtomicLong(0L);
    
    private final MockRepository<Long, BestRouteRequest> requests = new MockRepositoryImpl<>();
    
    private final MockRepository<Long, Response> responses = new MockRepositoryImpl<>();
    
    private final UserRepository<Long, String> users = new UserRepository<>();
    

	@PostMapping("/best-route")
	public Response newRequest(@RequestBody BestRouteRequest request){
        LOGGER.info(request.toString());
        
        //save request to db
        requests.save(idGenerator.getAndIncrement(), request);
        //db, change 0 to smth else to throw UserNotFoundException
        request.setxSecret(users.getxSecret(0L));
        //authorization
        if(request.getxSecret() == null || !request.getxSecret().equals("Mileus")){
            return new FailedResponse("Anauthorized request.");
        }
        //input validation
        if(!rv.isValid(request)){
            return new FailedResponse("Bad request.");
        }
        
        Response response;
        try{
            response = processor.processRequest(request);
            LOGGER.info(response.toString());
            responses.save(request.getId(), response);
        }catch(Exception ex){
            LOGGER.severe(ex.getMessage());
            return new FailedResponse("Request processing error. "+ex.getMessage());
        }
        return response;
    }
        
    @GetMapping("/api/requests")
    public List<BestRouteRequest>  allRequests(){
        return requests.findAll();
    }
    
    @GetMapping("/api/routes{id}")
    public Response  response(@PathVariable Long id){
        return responses.findById(id);
    }
    
}




  
        


