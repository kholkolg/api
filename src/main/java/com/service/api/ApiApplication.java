package com.service.api;


import java.io.IOException;
import java.net.URISyntaxException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;




@SpringBootApplication//(scanBasePackages = {"com.service.api"})
public class ApiApplication {
    
       public static void main(String[] args) throws IOException, URISyntaxException {
		
        SpringApplication.run(ApiApplication.class, args);
        
	}

}
