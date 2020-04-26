package com.wrhstrnsfr.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wrhstrnsfr.api.service.JwtService;

import io.jsonwebtoken.Claims;

@RestController()
@RequestMapping("/api/test")
public class TestController {

	@Autowired
	private JwtService jwtService;
	
	@GetMapping("/jwt")
	public String jwt() {
		return jwtService.generate(String.valueOf(System.currentTimeMillis()), "syahid");
	}
	
	@GetMapping("/jwt-claim")
	public Claims jwtClaim() {
		return jwtService.decodeJWT(jwtService.generate(String.valueOf(System.currentTimeMillis()), "syahid"));
	}
}
