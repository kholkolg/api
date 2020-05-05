package com.service.api;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.service.api.bestRoute.RequestProcessor;
import com.service.api.db.MockRepositoryImpl;
import com.service.api.db.UserRepository;
import com.service.api.rest.response.FailedResponse;
import com.service.api.rest.request.BestRouteRequest;
import com.service.api.rest.request.BestRouteRequestValidator;
import com.service.api.rest.response.Response;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    
    @Autowired
    @Qualifier("bestRouteProcessor")
    private RequestProcessor bestRouteProcessor;
 
    @Autowired
    @Qualifier("bestRouteValidator")
    private BestRouteRequestValidator bestRouteValidator;
    
    //Database
    private final AtomicLong idGenerator = new AtomicLong(0L);
    
    @Autowired
    @Qualifier("mockRepository")
    private MockRepositoryImpl<Long, BestRouteRequest> requests;
    
    @Autowired
    @Qualifier("mockRepository")
    private MockRepositoryImpl<Long, Response> responses;
    
    @Autowired
    @Qualifier("userRepository")
    private UserRepository<Long, String> users;
    

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
        if(!bestRouteValidator.isValid(request)){
            return new FailedResponse("Bad request.");
        }
        
        Response response;
        try{
            response = bestRouteProcessor.processRequest(request);
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




  
        


