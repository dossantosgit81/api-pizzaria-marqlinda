package com.pizzariamarqlinda.api_pizzaria_marqlinda.service;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.mapper.UserMapper;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Cart;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Role;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.User;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.LoginReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.LoginResDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.enums.ProfilesUserEnum;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {

    @InjectMocks
    private LoginService service;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private PasswordEncoder encoder;

    private User userSuccess;
    LoginReqDto loginReqDtoSuccess;

    @BeforeEach
    void setup(){
        encoder = new BCryptPasswordEncoder();
        service.setJwtEncoder(jwtEncoder());
        service.setEncoder(encoder);

        userSuccess = User.builder()
                .id(1L)
                .cart(new Cart())
                .name("Rafael")
                .lastName("Santos")
                .email("test@gmail.com")
                .phone("123456789")
                .password(encoder.encode("123456"))
                .roles(Set.of(Role.builder().name(ProfilesUserEnum.COMMON_USER).id(1L).build()))
                .build();
        loginReqDtoSuccess = new LoginReqDto("test@gmail.com", "123456");

    }

    @Test
    void shouldLogInSuccessfully(){
        when(userService.findByEmail(anyString())).thenReturn(userSuccess);
        LoginResDto res = service.login(loginReqDtoSuccess);
        Assertions.assertNotNull(res.token());
        Assertions.assertNotNull(res.expiresIn());
    }

    public static JwtEncoder jwtEncoder() {
        // Define uma chave secreta para assinar o JWT
        String secretKey = "my-super-secret-key-my-super-secret-key"; // Deve ter pelo menos 32 bytes
       // SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");

        // Cria a chave JWK
       // JWK jwk = new OctetSequenceKey.Builder(secretKeySpec).algorithm(com.nimbusds.jose.JWSAlgorithm.HS256).build();
        JWKSource<SecurityContext> jwkSource = new ImmutableSecret<>(secretKey.getBytes());

        // Retorna o JwtEncoder configurado
        return new NimbusJwtEncoder(jwkSource);
    }

}
