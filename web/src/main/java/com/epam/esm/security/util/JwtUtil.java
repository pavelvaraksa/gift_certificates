package com.epam.esm.security.util;

import com.epam.esm.security.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.jsonwebtoken.Claims.SUBJECT;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final JwtConfig jwtConfig;
    private final String ROLE = "role";

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtConfig.getAccessSecret()).parseClaimsJws(token).getBody();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(SUBJECT, userDetails.getUsername());
        claims.put(ROLE, getRoles(userDetails));
        return generateToken(claims);
    }

    private String generateToken(Map<String, Object> claims) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getAccessSecret())
                .compact();
    }

    private Date generateExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MILLISECOND, jwtConfig.getAccessExpiration().intValue());
        return calendar.getTime();
    }

    private Boolean isTokenExpired() {
        Date expiration = generateExpirationDate();
        return expiration.before(new Date());
    }

    public String getUsernameFromRefreshToken(String token) {
        return getClaimFromRefreshToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromRefreshToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromRefreshToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromRefreshToken(String token) {
        return Jwts.parser().setSigningKey(jwtConfig.getRefreshSecret()).parseClaimsJws(token).getBody();
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(SUBJECT, userDetails.getUsername());
        return generateRefreshToken(claims);
    }

    private String generateRefreshToken(Map<String, Object> claims) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDateForRefresh())
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getRefreshSecret())
                .compact();
    }

    private Date generateExpirationDateForRefresh() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, jwtConfig.getRefreshExpiration().intValue());
        return calendar.getTime();
    }

    private List<String> getRoles(UserDetails userDetails) {
        return userDetails.getAuthorities().
                stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }
}
