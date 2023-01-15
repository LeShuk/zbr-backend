package pro.zubrilka.zbrbackend.security.component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pro.zubrilka.zbrbackend.security.service.JwtTokenService;

import java.io.IOException;
import java.util.Collections;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeaderToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeaderToken != null) {
            String username = jwtTokenService.getUsername(authHeaderToken);

            //Пользователь есть, не аутентифицирован, и представлен в сервисе UserDetails
            if ((username != null)
                    && (SecurityContextHolder.getContext().getAuthentication() == null)
                    && (jwtTokenService.validateToken(authHeaderToken, userDetailsService.loadUserByUsername(username)))) {
                //Тогда аутентифицируем
                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }
        filterChain.doFilter(request, response);
    }
}
