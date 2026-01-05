package com.agroveterinaria.gestion.controller;

import com.agroveterinaria.gestion.dto.request.LoginRequestDTO;
import com.agroveterinaria.gestion.dto.response.LoginResponseDTO;
import com.agroveterinaria.gestion.model.Usuario;
import com.agroveterinaria.gestion.repository.UsuarioRepository;
import com.agroveterinaria.gestion.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getNombreUsuario(), request.getContrasena())
        );

        Usuario usuario = usuarioRepository.findByNombreUsuario(request.getNombreUsuario())
                .orElseThrow();

        String jwtToken = jwtService.generateToken(
                new org.springframework.security.core.userdetails.User(
                        usuario.getNombreUsuario(),
                        usuario.getContrasena(),
                        usuario.isActivo(), true, true, true,
                        java.util.Collections.emptyList()
                )
        );

        LoginResponseDTO response = new LoginResponseDTO();
        response.setToken(jwtToken);
        response.setNombreUsuario(usuario.getNombreUsuario());
        response.setRol(usuario.getRol().name());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Usuario> registrar(@RequestBody Usuario usuario) {
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        return ResponseEntity.ok(usuarioRepository.save(usuario));
    }
}