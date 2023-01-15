package pro.zubrilka.zbrbackend.security.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pro.zubrilka.zbrbackend.security.dto.AccountDTO;
import pro.zubrilka.zbrbackend.security.dto.JwtTokenDTO;
import pro.zubrilka.zbrbackend.security.service.JwtTokenService;

@RestController
@RequestMapping("/api/security")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @PostMapping("/authenticate")
    public JwtTokenDTO getToken(@RequestBody @Valid AccountDTO accountDTO) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(accountDTO.getEmail(), accountDTO.getPassword());

        //Тут кинет ошибку, если ползователя нет в UserDetailsService...
        Authentication auth = authenticationManager.authenticate(authenticationToken);

        return new JwtTokenDTO(jwtTokenService.generateHeaderToken(auth.getName()));
    }


}
