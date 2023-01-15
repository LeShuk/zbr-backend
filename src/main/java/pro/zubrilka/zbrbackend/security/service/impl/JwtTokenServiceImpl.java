package pro.zubrilka.zbrbackend.security.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pro.zubrilka.zbrbackend.security.service.JwtTokenService;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtTokenServiceImpl implements JwtTokenService {

    private static final String PREFIX = "Bearer_";
    //10 минут
    private static final long EXPIRATION_TIME = 600_000L;
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Override
    public String getUsername(String headerToken) {
        return getClaimFromToken(headerToken, Claims::getSubject);
    }

    @Override
    public Date getExpirationDate(String headerToken) {
        return getClaimFromToken(headerToken, Claims::getExpiration);
    }

    @Override
    public <T> T getClaimFromToken(String headerToken, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(headerToken);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String headerToken) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(headerToken.replace(PREFIX, ""))
                .getBody();
    }

    @Override
    public String generateHeaderToken(String username) {
        return  PREFIX + Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    @Override
    public Boolean validateToken(String headerToken, UserDetails userDetails) {
        final String username = getUsername(headerToken);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(headerToken));
    }

    private Boolean isTokenExpired(String headerToken) {
        final Date expiration = getExpirationDate(headerToken);
        return expiration.before(new Date());
    }
}
