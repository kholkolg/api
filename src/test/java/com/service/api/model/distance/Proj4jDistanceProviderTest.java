/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.model.distance;

import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Olga Kholkovskaia 
 */
public class Proj4jDistanceProviderTest {
    
    public Proj4jDistanceProviderTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getDistanceMeters method, of class Proj4jDistanceProvider.
     */
    @Test
    public void testGetDistanceMeters0() {
        System.out.println("getDistanceMeters");
        double[] origin = new double[]{14.496117, 50.030460};
        double[] destination = new double[]{14.496117, 50.030460};
        Proj4jDistanceProvider instance = new Proj4jDistanceProvider();
        double expResult = 0.0;
        double result = instance.getDistanceMeters(origin, destination);
        assertEquals(expResult, result, 0.0001);
        fail(String.format("Distance test failed. Expected={}, real={}" ,expResult, result));
    }
    
    @Test
    public void testGetDistanceMeters1() {
        System.out.println("getDistanceMeters");
        double[] origin = new double[]{14.496117, 50.030460};
        double[] destination = new double[]{14.4942415, 50.0238942};
        Proj4jDistanceProvider instance = new Proj4jDistanceProvider();
        double expResult = 0.0;
        double result = instance.getDistanceMeters(origin, destination);
        assertEquals(expResult, result, 0.0001);
        fail(String.format("Distance test failed. Expected={}, real={}" ,expResult, result));
    }
    
    @Test
    public void testGetDistanceMeters2() {
        System.out.println("getDistanceMeters");
        double[] origin = new double[]{0, 0};
        double[] destination = new double[]{14.496117, 50.030460};
        Proj4jDistanceProvider instance = new Proj4jDistanceProvider();
        double expResult = 0.0;
        double result = instance.getDistanceMeters(origin, destination);
        assertEquals(expResult, result, 0.0001);
        fail(String.format("Distance test failed. Expected={}, real={}" ,expResult, result));
    }
    
        @Test
    public void testGetDistanceMeters3() {
        System.out.println("getDistanceMeters");
        double[] origin = new double[]{50.030460, 14.496117};
        double[] destination = new double[]{14.496117, 50.030460};
        Proj4jDistanceProvider instance = new Proj4jDistanceProvider();
        double expResult = 0.0;
        double result = instance.getDistanceMeters(origin, destination);
        assertEquals(expResult, result, 0.0001);
        fail(String.format("Distance test failed. Expected={}, real={}" ,expResult, result));
    }

    /**
     * Test of getPoint method, of class Proj4jDistanceProvider.
     */
    @Test
    public void testFindPoint() {
        System.out.println("getPoint");
        double[] origin = new double[]{14.496117, 50.030460};
        double[] destination = new double[]{14.4942415, 50.0238942};
        double distFromOrigin = 0.0;
        Proj4jDistanceProvider instance = new Proj4jDistanceProvider();
        double[] expResult = new double[]{14.496117, 50.030460};
        double[] result = instance.getPoint(origin, destination, distFromOrigin);
        assertArrayEquals(result, expResult, 0.001);
        // TODO review the generated test code and remove the default call to fail.
       fail(String.format("Distance test failed. Expected={}, real={}" , Arrays.toString(expResult),
           Arrays.toString(result)));
    }
    
    @Test
    public void testFindPoint1() {
        System.out.println("getPoint");
        double[] origin = new double[]{14.496117, 50.030460};
        double[] destination = new double[]{14.4942415, 50.0238942};
        double distFromOrigin = 0.0;
        Proj4jDistanceProvider instance = new Proj4jDistanceProvider();
        double[] expResult = new double[]{14.496117, 50.030460};
        double[] result = instance.getPoint(origin, destination, distFromOrigin);
        assertArrayEquals(result, expResult, 0.001);
        // TODO review the generated test code and remove the default call to fail.
       fail(String.format("Distance test failed. Expected={}, real={}" , Arrays.toString(expResult),
           Arrays.toString(result)));
    }

    /**
     * Test of getDistanceFromPoint1 method, of class Proj4jDistanceProvider.
     */
    @Test
    public void testGetDistanceFromPoint2() {
        System.out.println("getDistanceFromPoint1");
        double[] point1 = new double[]{14.496117, 50.030460};
        double[] point2 = new double[]{14.4942415, 50.0238942};
        double[] destination = new double[]{14.492278, 50.0410019};
        
        double distToDestination = 20.0;
        Proj4jDistanceProvider instance = new Proj4jDistanceProvider();
        double expResult = 0.0;
        double result = instance.getDistanceFromPoint1(point1, point2, destination, distToDestination);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
       fail(String.format("Distance test failed. Expected={}, real={}" ,expResult, result));
    }
    
  
    
}
