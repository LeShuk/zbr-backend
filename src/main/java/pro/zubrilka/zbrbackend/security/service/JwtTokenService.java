package pro.zubrilka.zbrbackend.security.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.function.Function;

public interface JwtTokenService {
    String getUsername(String token);
    Date getExpirationDate(String token);
    <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver);
    String generateHeaderToken(String username);
    Boolean validateToken(String token, UserDetails userDetails);
}
