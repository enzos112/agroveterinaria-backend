package com.agroveterinaria.gestion.security;

import com.agroveterinaria.gestion.model.Usuario;
import com.agroveterinaria.gestion.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("--- DIAGNÓSTICO LOGIN INICIO ---");
        System.out.println("1. Buscando usuario: " + username);

        Usuario usuario = usuarioRepository.findByNombreUsuario(username)
                .orElseThrow(() -> {
                    System.out.println("!!! Usuario NO encontrado en BD");
                    return new UsernameNotFoundException("Usuario no encontrado");
                });

        System.out.println("2. Usuario encontrado: " + usuario.getNombreUsuario());

        System.out.println("3. Hash en Base de Datos: " + usuario.getContrasena());

        System.out.println("4. Rol del usuario: " + usuario.getRol());
        System.out.println("--- DIAGNÓSTICO LOGIN FIN ---");

        return org.springframework.security.core.userdetails.User
                .withUsername(usuario.getNombreUsuario())
                .password(usuario.getContrasena())
                .roles(usuario.getRol().toString())
                .build();
    }
}