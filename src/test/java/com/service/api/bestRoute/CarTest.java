/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.bestRoute;

import com.fasterxml.jackson.databind.JsonNode;
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
@ContextConfiguration(classes = CarTest.TestConfig.class)
public class CarTest {
    
    @Configuration
    @ComponentScan("com.service")
    public static class TestConfig {

}
    final static double EPS = 5.0;
    
    final static String dir= "/home/olga/NetBeansProjects/api/data/";
    
    final static String[] files = new String[]{"Point A", "Point B", "Point C"};
    
    @Autowired
    CarBuilder carBuilder;
    
    double[] times = new double[]{0, 10, 20, 100, 300, 500, 1200, 2214, 2467, 2346}; 
    double[] resultsA = new double[]{11235, 11225, 11215, 11140, 11625, 10910, 6005, 0.0, 0.0, 0.0};
    double[] resultsB = new double[]{11235, 11225, 11215, 11135, 11160, 9920,  6650, 330, 0.0, 50};

       
//    double[] times = new double[]{210, 211, 212, 213, 229, 230, 231,250, 251, 252, 253, 518,519, 520};
    
    
    public static Route readRoute(String filename){
        ObjectMapper mapper = new ObjectMapper();
        Route route = null; 
         try(BufferedReader br = new BufferedReader(new FileReader(String.format("%s%s.json", dir, filename)))){
            JsonNode  root = mapper.readTree(br);
            JsonNode routeObj = root.get("routes").get(0);
            route = mapper.convertValue(routeObj, Route.class);
            route.setName(filename);
        } catch (IOException ex) {
            Logger.getLogger(CarTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return route;   
    }
    
    public CarTest() {
    }



    /**
     * Test of movefromStart method, of class Car.
     */
    @Test
    public void testMovefromStartA() {
        System.out.println("movefromStart: Point A");
        boolean all_passed = true;
        Route route = readRoute(files[0]);
        Car car = carBuilder.getCar(route);
        for(int i = 0; i < times.length; i++){
            double time =  times[i];
            double exp_result = 10816;
            double result = car.movefromStart(time);
             System.out.println(result + " "+ exp_result);
            if(Math.abs(result - exp_result) > EPS){
                System.out.println(time+": " + result + " "+ exp_result);
                all_passed = false;
            }
        }
      assertTrue(all_passed);
    }
    
    /**
     * Test of movefromStart method, of class Car.
     */
    @Test
    public void testMovefromStartB() {
        System.out.println("movefromStart: Point C");
        boolean all_passed = true;
        Route route = readRoute(files[2]);
        Car car = carBuilder.getCar(route);
        for(int i = 0; i < times.length; i++){
            double time = times[i];
            double exp_result = 10816;// resultsB[i];
            double result = car.movefromStart(time);
            if(Math.abs(result - exp_result) > EPS){
                System.out.println(time+": "+result + " "+ exp_result); 
                all_passed = false;
            }
        }
      assertTrue(all_passed);
    }
}
