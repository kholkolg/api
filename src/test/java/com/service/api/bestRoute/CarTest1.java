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
 * Simple linear route along the axis 
 * @author Olga Kholkovskaia 
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =  CarTest1.CarTest1Config.class)
public class CarTest1{
    
    @Configuration
    @ComponentScan("com.service")
    public static class CarTest1Config {
        }
    
    public CarTest1() {
        }
    
    final static double EPS = 3;
    
    final static String file= "/home/olga/NetBeansProjects/api/data/route1.json";
    
    
    @Autowired
    CarBuilder carBuilder;
    
    public Route readRoute(String filename){
        ObjectMapper mapper = new ObjectMapper();
        Route route = null; 
         try(BufferedReader br = new BufferedReader(new FileReader(filename))){
            route = mapper.convertValue(mapper.readTree(br), Route.class);
            route.setName(filename);
        } catch (IOException ex) {
            Logger.getLogger(CarTest1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return route;   
    }
    
    
    //part1: move from start for some time
    double[] times =    new double[]{0, 4, 5, 8, 10, //step1
                                    11.46, 13, 15.5, 16,//step2
                                    17.7, 20.3, 20.85, 21,//step3
                                    23}; 
    double[] distanceTravelled = new double[]{0, 4*4, 5*4, 8*4, 40,
                                            40+1.46*5, 40+3*5, 40+5.5*5, 70,
                                            70+1.7*6, 70+4.3*6, 70+4.85*6, 100,
                                            100};
 
    @Test
    public void testMovefromStart() {
        System.out.println("movefromStart: ");
        boolean all_passed = true;
        Route route = readRoute(file);
        Car car = carBuilder.getCar(route);
        for(int i = 0; i < times.length; i++){
            double time =  times[i];
            double exp_result = route.getDistance() - distanceTravelled[i];
            double result = car.movefromStart(time);
              System.out.println(result + " "+ exp_result);
            if(Math.abs(result - exp_result) > EPS){
                System.out.println(result + " "+ exp_result);
                all_passed = false;
            }
        }
      assertTrue(all_passed);
     }
    
    // part2: find delay
    double[] distToDestination = new double[]{100, 80, 60, 45, 30, 14, 0};     
    double[] delays = new double[]{0, 5, 10, 14, 16, 19.3,  21};  
    
    @Test
    public void testComputeDelay() {
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
