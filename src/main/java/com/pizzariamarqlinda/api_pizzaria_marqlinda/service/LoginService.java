package com.pizzariamarqlinda.api_pizzaria_marqlinda.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.ObjectNotFoundException;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Role;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.User;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.LoginReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.LoginResDto;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserService userService;
    @Setter
    @Autowired
    private PasswordEncoder encoder;
    @Setter
    @Autowired
    private JwtEncoder jwtEncoder;

    public LoginResDto login(LoginReqDto req){
        String INVALID_USER = "Usuário e/ou senha inválido(s).";
        try {
            User user = userService.findByEmail(req.email());
            if(!user.isValidUser(req, encoder)){
                throw new BadCredentialsException(INVALID_USER);
            }
            return loginRes(user);
        }catch (ObjectNotFoundException ex){
            throw new BadCredentialsException(INVALID_USER);
        }
    }

    private LoginResDto loginRes(User user){
        Set<String> scopes = new HashSet<>();
        for (Role role : user.getRoles())
            scopes.add(role.getName().getName());
        // 5 dias
        var expiresIn = 432000L;
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("mybackend")
                .subject(user.getId().toString())
                .expiresAt(Instant.now().plusSeconds(expiresIn))
                .issuedAt(Instant.now())
                .claim("scope", scopes)
                .build();
        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return new LoginResDto(jwtValue, expiresIn);
    }
}
