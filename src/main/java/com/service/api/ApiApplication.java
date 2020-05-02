package com.service.api;



import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;




@SpringBootApplication
public class ApiApplication {
    
       public static void main(String[] args) throws IOException, URISyntaxException {
		
        SpringApplication.run(ApiApplication.class, args);
        
	}

}
