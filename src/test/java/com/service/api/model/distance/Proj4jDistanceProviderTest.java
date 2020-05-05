/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.model.distance;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.api.routing.FIlERouteProvider;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 */
public class Proj4jDistanceProviderTest {
    
    public Proj4jDistanceProviderTest() {
    }
    
    final String dir= "/home/olga/NetBeansProjects/api/data/";
    
    public List<Map<String, Double>> readPoints(String filename){
        StringBuilder sb = new StringBuilder();
        List<Map<String, Double>> data = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(dir+filename))){
             
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root =  mapper.readTree(br);
                int num_points = root.get("x1").size();
                for(int i = 0; i < num_points; i++){
                    String key = String.valueOf(i);
                    Map<String, Double> map = new HashMap<>();
                    map.put("x1", root.get("x1").get(key).asDouble());
                    map.put("y1", root.get("y1").get(key).asDouble());
                    map.put("x2", root.get("x2").get(key).asDouble());
                    map.put("y2", root.get("y2").get(key).asDouble());
                    map.put("distance", root.get("distance").get(key).asDouble());
                    data.add(map);
                }
        }catch (IOException ex) {
            Logger.getLogger(FIlERouteProvider.class.getName()).log(Level.SEVERE, null, ex);
       }       
        return data;
    }
    
    
    /**
     * Distance between two points in wsg.
     *  
     */
    @Test
    public void testGetDistanceMeters1() {
        System.out.println("getDistanceMeters: projected data");
        boolean all_passed = true;
        Proj4jDistanceProvider instance = new Proj4jDistanceProvider();
        List<Map<String, Double>> data = readPoints("test_point1.json");
        
        // same point 
        double dist = instance.getDistanceMeters(new double[]{data.get(0).get("x1"), data.get(0).get("y1")}, 
            new double[]{data.get(0).get("x1"), data.get(0).get("y1")});
        if(dist != 0){
             System.out.println("Failed case: same point " +dist+" != "+0);
             all_passed = false;
        }
        //
        for(Map<String, Double> d: data){
                dist = instance.getDistanceMeters(new double[]{d.get("x1"), d.get("y1")}, 
                new double[]{d.get("x2"), d.get("y2")});
                double exp_result = d.get("distance");
                if(Math.abs(dist - exp_result) > 1){
                    System.out.println("Failed case " +dist+" != "+exp_result+"; "+d.toString());
                    all_passed = false;
                }
       }
        assertTrue(all_passed);
    }
    
    @Test
    public void testGetDistanceMeters2() {
        System.out.println("getDistanceMeters: real data");
        boolean all_passed = true;
        Proj4jDistanceProvider instance = new Proj4jDistanceProvider();
        List<Map<String, Double>> data = readPoints("test_point2.json");
        
        for(Map<String, Double> d: data){
                double dist = instance.getDistanceMeters(new double[]{d.get("x1"), d.get("y1")}, 
                new double[]{d.get("x2"), d.get("y2")});
                double exp_result = d.get("distance");
                if(Math.abs(dist - exp_result) > 5){
                    System.out.println("Failed case " +dist+" != "+exp_result+"; "+d.toString());
                    all_passed = false;
                }
       }
        assertTrue(all_passed);
    }
    

//    /**
//     * Test of getPoint method, of class Proj4jDistanceProvider.
//     */
//    @Test
//    public void testGetPoint() {
//        System.out.println("getPoint");
//        double[] origin = null;
//        double[] destination = null;
//        double distFromOrigin = 0.0;
//        Proj4jDistanceProvider instance = new Proj4jDistanceProvider();
//        double[] expResult = null;
//        double[] result = instance.getPoint(origin, destination, distFromOrigin);
//        assertArrayEquals(expResult, result, 0.01);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDistanceFromPoint1 method, of class Proj4jDistanceProvider.
//     */
//    @Test
//    public void testGetDistanceFromPoint1() {
//        System.out.println("getDistanceFromPoint1");
//        double[] point1 = null;
//        double[] point2 = null;
//        double[] destination = null;
//        double distToDestination = 0.0;
//        Proj4jDistanceProvider instance = new Proj4jDistanceProvider();
//        double expResult = 0.0;
//        double result = instance.getDistanceFromPoint1(point1, point2, destination, distToDestination);
//        assertEquals(expResult, result, 0.0);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//    
}
