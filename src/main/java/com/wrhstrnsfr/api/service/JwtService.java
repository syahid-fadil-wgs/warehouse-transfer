package com.wrhstrnsfr.api.service;

import io.jsonwebtoken.Claims;

public interface JwtService {

	public String generate(String id, String subject);
	
	public Claims decodeJWT(String jwt);
	
}
