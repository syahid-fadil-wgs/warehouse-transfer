package com.wrhstrnsfr.api.service.impl;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.wrhstrnsfr.api.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtServiceImpl implements JwtService {

	@Value("${jwt.expired}")
    private String jwtExpired;
	
	@Value("${jwt.issuer}")
	private String jwtIssuer;
	
	@Value("${jwt.secret.key}")
	private String jwtSecretKey;
	
	@Override
	public String generate(String id, String subject) {
		//The JWT signature algorithm we will be using to sign the token
	    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

	    long nowMillis = System.currentTimeMillis();
	    Date now = new Date(nowMillis);

	    //We will sign our JWT with our ApiKey secret
	    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtSecretKey);
	    Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

	    //Let's set the JWT Claims
	    JwtBuilder builder = Jwts.builder().setId(id)
	            .setIssuedAt(now)
	            .setSubject(subject)
	            .setIssuer(jwtIssuer)
	            .signWith(signatureAlgorithm, signingKey);
	  
	    //if it has been specified, let's add the expiration
	    Long ttlMillis = Long.parseLong(jwtExpired);
	    if (ttlMillis > 0) {
	        long expMillis = nowMillis + ttlMillis;
	        Date exp = new Date(expMillis);
	        builder.setExpiration(exp);
	    }  
	  
	    //Builds the JWT and serializes it to a compact, URL-safe string
	    return builder.compact();
	}

	@Override
	public Claims decodeJWT(String jwt) {

	    Claims claims = Jwts.parser()
            .setSigningKey(DatatypeConverter.parseBase64Binary(jwtSecretKey))
            .parseClaimsJws(jwt).getBody();
	    
	    return claims;
	}

}
