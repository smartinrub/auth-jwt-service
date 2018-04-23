package org.smartinrub.authservice;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.smartinrub.authservice.SecurityConstants.*;

@RestController
public class JwtController {

    private static final String USERNAME = "username";

    @PostMapping("/add")
    public void addAuthentication(HttpServletResponse response, String email) {

        Map<String, Object> claims = new HashMap<>();
        claims.put(USERNAME, "World");

        response.addHeader(HEADER_STRING, TOKEN_PREFIX + createToken(email, claims));
    }

    @PostMapping("/get")
    public String getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);

        if (token != null) {
            Claims claims = Jwts.parser()
                    .setSigningKey(JWT_SECRET)
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.get(USERNAME).toString();
            if (username != null) {
                return username;
            }
        }

        return null;
    }

    private String createToken(String email, Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email).setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();
    }

}
