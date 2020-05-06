/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.bestRoute;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.api.model.Route;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *  
 * @author Olga Kholkovskaia 
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =  CarTest2.CarTest2Config.class)
public class CarTest2{
    
    @Configuration
    @ComponentScan("com.service")
    public static class CarTest2Config {
        }
    
    public CarTest2() {
        }
    
    final static double EPS = 3;
    
    final static String file= "/home/olga/NetBeansProjects/api/data/route3.json";
    
    
    public Route readRoute(String filename){
        ObjectMapper mapper = new ObjectMapper();
        Route route = null; 
         try(BufferedReader br = new BufferedReader(new FileReader(filename))){
            route = mapper.convertValue(mapper.readTree(br), Route.class);
            route.setName(filename);
        } catch (IOException ex) {
            Logger.getLogger(CarTest2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return route;   
    }
    
    @Autowired
    CarBuilder carBuilder;
    
   //part1: move from start for some time
    double[] times =    new double[]{0, 32, 64, 96, 128, 160}; 
    
    double[] distToDestination = new double[]{100, 80, 60, 40, 20, 0};
    
    
    @Test
    public void testMovefromStart() {
        System.out.println("movefromStart: ");
        boolean all_passed = true;
        Route route = readRoute(file);
        Car car = carBuilder.getCar(route);
        for(int i = 0; i < times.length; i++){
            double time =  times[i];
            double exp_result = distToDestination[i];
            double result = car.movefromStart(time);
             System.out.println(result + " "+ exp_result);
            if(Math.abs(result - exp_result) > EPS){
                System.out.println(result + " "+ exp_result);
                all_passed = false;
            }
        }
      assertTrue(all_passed);
     }
    
     /**
     *
     */
    @Test
    public void testComputeDelay() {
        
//        double[] distToDestination = new double[]{100, 80, 60, 40, 20, 0};     
        double[] delays = new double[]{0, 32, 64, 96, 128, 160};  
//0.5499999807892377 0.0
//28.834271211223825 32.0
//42.426406864217334 64.0
//77.11854248720428 96.0
//97.11854247933157 128.0
//111.21067811872163 160.0

        System.out.println("computeDelay: ");
        boolean all_passed = true;
        Route route = readRoute(file);
      
        for(int i = 0; i < delays.length ; i++){
            Car car = carBuilder.getCar(route);
            double targetDist = distToDestination[i];
            
            double exp_result = delays[i];
            double result = car.computeDelay(targetDist, 0.1);
            System.out.println(result + " "+ exp_result);
            if(Math.abs(result - exp_result) > EPS){
                System.out.println(result + " "+ exp_result);
                all_passed = false;
            }
        }
      assertTrue(all_passed);
     }
   
    
    
   
}
