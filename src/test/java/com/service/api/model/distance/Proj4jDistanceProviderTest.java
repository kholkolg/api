/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.model.distance;

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
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Olga Kholkovskaia 
 */
public class Proj4jDistanceProviderTest {
    final String dir= "/home/olga/NetBeansProjects/api/data/";
    
    // error in meters
    public static final double EPS1 = 1;//generated data
    
    public int EPS2 = 5;//real data
    
    Proj4jDistanceProvider instance = new Proj4jDistanceProvider();
    
    List<Map<String, Double>> data1 = readPoints("test_point1.json");
    List<Map<String, Double>> data2 = readPoints("test_point2.json");
    
    public Proj4jDistanceProviderTest() {
    }
        
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
     * Distance between two points.
     *  
     */
    @Test
    public void testGetDistanceMeters1() {
        System.out.println("getDistanceMeters: projected data");
        boolean all_passed = true;
        
        // same point 
        double result = instance.getDistanceMeters(new double[]{data1.get(0).get("x1"), data1.get(0).get("y1")}, 
            new double[]{data1.get(0).get("x1"), data1.get(0).get("y1")});
        if(result != 0){
             System.out.println("Failed case: same point " +result+" != "+0);
             all_passed = false;
        }
        //
        for(Map<String, Double> d: data1){
                result = instance.getDistanceMeters(new double[]{d.get("x1"), d.get("y1")}, 
                new double[]{d.get("x2"), d.get("y2")});
                double exp_result = d.get("distance");
                if(Math.abs(result - exp_result) > EPS1){
                    System.out.println("Failed case " +result+" != "+exp_result+"; "+d.toString());
                    all_passed = false;
                }
       }
        assertTrue(all_passed);
    }

    @Test
    public void testGetDistanceMeters2() {
        System.out.println("getDistanceMeters: real data");
        boolean all_passed = true;
        
        for(Map<String, Double> d: data2){
                double result = instance.getDistanceMeters(new double[]{d.get("x1"), d.get("y1")}, 
                new double[]{d.get("x2"), d.get("y2")});
                double exp_result = d.get("distance");
                if(Math.abs(result - exp_result) > EPS2 ){
                    System.out.println("Failed case " +result+" != "+exp_result+"; "+d.toString());
                    all_passed = false;
                }
       }
        assertTrue(all_passed);
    }
    

    /**
     * Test of getPoint method, of class Proj4jDistanceProvider.
     */
    @Test
    public void testGetPoint1() {
        System.out.println("getPoint: projected, start point");
        boolean all_passed = true;
        
        for(Map<String, Double> d: data1){
            double[] p1 = new double[]{d.get("x1"), d.get("y1")};
            double[] p2 = new double[]{d.get("x2"), d.get("y2")};
           //1) start point
            double[] result = instance.getPoint(p1, p2, 0);
            double dist = instance.getDistanceMeters(result, p1);
            if(dist > EPS1){
                all_passed = false;
                System.out.println("Failed case " +Arrays.toString(result)+" != "+
                    Arrays.toString(p1)+"; "+d.toString());
            }
        }
         assertTrue(all_passed);
    }
    
    
    @Test
    public void testGetPoint2() {
        System.out.println("getPoint: projected, end point");
        boolean all_passed = true;
        
        for(Map<String, Double> d: data1){
            double[] p1 = new double[]{d.get("x1"), d.get("y1")};
            double[] p2 = new double[]{d.get("x2"), d.get("y2")};
            double distance = d.get("distance");
           //1) start point
            double[] result = instance.getPoint(p1, p2, distance);
            double error = instance.getDistanceMeters(result, p2);
            if(error > EPS1){
                all_passed = false;
                System.out.println("Failed case " +Arrays.toString(result)+" != "+
                    Arrays.toString(p1)+"; "+d.toString());
            }
        }
         assertTrue(all_passed);
    }
    
    @Test
    public void testGetPoint3() {
        System.out.println("getPoint: projected, middle point");
        boolean all_passed = true;
        
        for(Map<String, Double> d: data1){
            double[] p1 = new double[]{d.get("x1"), d.get("y1")};
            double[] p2 = new double[]{d.get("x2"), d.get("y2")};
            double distance = d.get("distance")/2;
           //1) start point
            double[] result = instance.getPoint(p1, p2, distance);
            double new_distance = instance.getDistanceMeters(result, p2);
            if(Math.abs(distance-new_distance) > EPS1 ){
                all_passed = false;
                System.out.println("Failed case " +Arrays.toString(result)+" != "+
                    Arrays.toString(p1)+"; "+d.toString());
            }
        }
         assertTrue(all_passed);
    }
    
    @Test
    public void testGetPoint4() {
        System.out.println("getPoint: real");
        boolean all_passed = true;
        
        for(Map<String, Double> d: data2){
            double[] p1 = new double[]{d.get("x1"), d.get("y1")};
            double[] p2 = new double[]{d.get("x2"), d.get("y2")};
            double distance = d.get("distance");
            double length = d.get("distance")*Math.random();

            double[] result = instance.getPoint(p1, p2, length);
            double distanceP1 = instance.getDistanceMeters(result, p1);
            double distanceP2 = instance.getDistanceMeters(result, p2);
            if(Math.abs(length - distanceP1) > EPS2 || Math.abs(distance - length - distanceP2) > EPS2  ){
                all_passed = false;
                System.out.println("Failed case " +Arrays.toString(result)+" != "+
                    Arrays.toString(p1)+"; "+d.toString());
            }
        }
         assertTrue(all_passed);
    }
    

    /**
     * Test of getDistanceFromPoint1 method, of class Proj4jDistanceProvider.
     */
    @Test
    public void testGetDistanceFromPoint1() {
        System.out.println("getPoint: real");
        boolean all_passed = true;
            
        for(Map<String, Double> d: data1){
            double[] p1 = new double[]{d.get("x1"), d.get("y1")};
            double[] p2 = new double[]{d.get("x2"), d.get("y2")};
            double distance = d.get("distance");

            double result = instance.getDistanceFromPoint1(p1, p2, p2, 0);
            if(Math.abs(result)  > EPS1 ){
                all_passed = false;
                System.out.println("Failed case " + Math.abs(result) + " != " + distance +
                    Arrays.toString(p1)+"; "+d.toString());
            }
            
            result = instance.getDistanceFromPoint1(p1, p2, p2, distance);
            if(Math.abs(result)  > EPS1 ){
                all_passed = false;
                System.out.println("Failed case " + Math.abs(result) + " != " + distance +
                    Arrays.toString(p1)+"; "+d.toString());
            }
        }
         assertTrue(all_passed);
    }
}
