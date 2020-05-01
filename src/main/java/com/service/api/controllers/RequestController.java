/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.api.controllers;


import com.service.api.request.Request;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 *
 * @author Olga Kholkovskaia <olga.kholkovskaya@gmail.com>
 */
@RestController
public class RequestController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/request")
	public Request greeting(@RequestParam(value = "x-secret", defaultValue = "Mileus") String name) {
		return new Request();
	}

    
}
