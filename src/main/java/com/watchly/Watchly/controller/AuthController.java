package com.watchly.Watchly.controller;

import com.watchly.Watchly.config.JwtService;
import com.watchly.Watchly.dto.TokenDTO;
import com.watchly.Watchly.dto.UsuarioDTO;
import com.watchly.Watchly.model.UsuarioEntity;
import com.watchly.Watchly.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioService usuarioService;

    @PostMapping("/register")
    public TokenDTO register(@RequestBody UsuarioDTO.Request request) {
        return usuarioService.create(request);
    }

    @PostMapping("/login")
    public TokenDTO login(@RequestBody UsuarioDTO.LoginRequest request) {

        var authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getSenha()
                        )
                );

        UsuarioEntity usuario = (UsuarioEntity) authentication.getPrincipal();

        String token = jwtService.generateToken(usuario);

        return new TokenDTO(token);
    }

}