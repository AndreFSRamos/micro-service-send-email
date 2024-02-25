package com.ms.email.services;

import com.ms.email.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TokenService {
    @Value("${apiAuth.url}")
    private String AUTH_SERVICE_URL;

    public void validateToken(String token){
        try{
            token = recoverToken(token);
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Void> response = restTemplate.exchange(AUTH_SERVICE_URL, HttpMethod.GET, requestEntity, Void.class);

            if(response.getStatusCode() != HttpStatus.NO_CONTENT)
                throw new UnauthorizedException("Invalid token!");
            
        }catch (Exception error){
            System.err.println(error.getMessage());
            throw new UnauthorizedException("Invalid token!");
        }
    }

    public String recoverToken(String token){
        if(token == null) return null;
        if(token.contains("Bearer ")) return token.replace("Bearer ", "");
        return token;
    }
}