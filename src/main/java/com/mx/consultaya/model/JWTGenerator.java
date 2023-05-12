package com.mx.consultaya.model;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mx.consultaya.exception.CustomException;

public class JWTGenerator {
    private Algorithm algorithm = Algorithm.HMAC256("consulta-ya");

    /**
     * Genera un token JWT a partir de los datos de carga útil especificados.
     *
     * @param payload un mapa que contiene los datos de carga útil que se utilizarán
     *                para generar el token.
     * @return una cadena que representa el token JWT generado.
     * @throws JWTVerificationException si se produce un error al verificar el token
     *                                  JWT.
     */
    public String generateJWT(Map<String, String> payload) throws JWTVerificationException {
        Builder tokenBuilder = JWT.create()
                .withIssuer("http://consulta-ya.com.mx")
                .withClaim("jti", UUID.randomUUID().toString())
                .withExpiresAt(Date.from(Instant.now().plusSeconds(60)))
                .withIssuedAt(Date.from(Instant.now()));

        payload.entrySet().forEach(action -> tokenBuilder.withClaim(action.getKey(), action.getValue()));
        return tokenBuilder.sign(algorithm);
    }

    /**
     * @param decodedJWT
     * @return
     * @throws CustomException
     */
    public String refreshJWT(String id,String email){
        
        Map<String, String> payload = new HashMap<>();
        payload.put("_id", id);
        payload.put("correoElectronico", email);
        String newJti = UUID.randomUUID().toString();
        // 3600
        Date newExpirationDate = Date.from(Instant.now().plusSeconds(3600));

        Builder tokenBuilder = JWT.create()
                .withIssuer("http://consulta-ya.com.mx")
                .withPayload(payload)
                .withClaim("jti", newJti)
                .withExpiresAt(newExpirationDate)
                .withIssuedAt(Date.from(Instant.now()));
            payload.entrySet().forEach(action -> tokenBuilder.withClaim(action.getKey(), action.getValue()));
        return tokenBuilder.sign(algorithm);
    }

    /**
     * Desencripta el token JWT especificado y devuelve el objeto DecodedJWT
     * resultante.
     *
     * @param jwt una cadena que representa el token JWT que se va a desencriptar.
     * @return el objeto DecodedJWT que representa el token JWT desencriptado.
     * @throws CustomException si se produce un error al desencriptar el token JWT.
     */
    public DecodedJWT decriptJWT(String jwt) throws CustomException {
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();

            return verifier.verify(jwt);
        } catch (Exception e) {
            throw new CustomException("Error de autenticación", e);
        }
    }
}
