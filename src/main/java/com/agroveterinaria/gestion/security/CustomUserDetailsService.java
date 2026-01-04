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
        Usuario usuario = usuarioRepository.findByNombreUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // Convertimos nuestro Usuario (Entity) al UserDetails (Security)
        // Agregamos el prefijo "ROLE_" porque Spring Security lo espera así por defecto
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name());

        return new User(
                usuario.getNombreUsuario(),
                usuario.getContrasena(),
                usuario.isActivo(),
                true, true, true, // accountNonExpired, etc. (lo dejamos en true por simplicidad)
                Collections.singletonList(authority)
        );
    }
}