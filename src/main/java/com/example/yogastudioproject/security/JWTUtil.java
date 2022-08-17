package com.example.yogastudioproject.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JWTUtil {

    public String generateToken(String email) {

        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(60).toInstant());

        return JWT.create()
                .withSubject("User details")
                .withClaim("email", email)
                .withIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .withIssuer("YogaStudio")
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(SecurityConstants.SECRET));
    }

    public String validateTokenAndRetrieveClaim(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SecurityConstants.SECRET))
                .withSubject("User details")
                .withIssuer("YogaStudio")
                .build();

        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getClaim("email").asString();

    }
}
