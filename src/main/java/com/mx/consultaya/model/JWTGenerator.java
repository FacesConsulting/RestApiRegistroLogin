package com.mx.consultaya.model;

import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTGenerator {
    private Algorithm algorithm = Algorithm.HMAC256("consulta-ya");

    /**
     * @param payload
     * @return token
     * @throws JWTVerificationException
     */

    public String generateJWT(Map<String, String> payload) throws JWTVerificationException {
        Builder tokenBuilder = JWT.create()
                .withIssuer("http://consulta-ya.com.mx")
                .withClaim("jti", UUID.randomUUID().toString())
                .withExpiresAt(Date.from(Instant.now().plusSeconds(3600)))
                .withIssuedAt(Date.from(Instant.now()));

        payload.entrySet().forEach(action -> tokenBuilder.withClaim(action.getKey(), action.getValue()));

        return tokenBuilder.sign(algorithm);

    }

    public DecodedJWT decriptJWT(String jwt) throws Exception {
        JWTVerifier verifier = JWT.require(algorithm)
                .build();
                
        return verifier.verify(jwt);
    }
}
