package com.agroveterinaria.gestion.config;

import com.agroveterinaria.gestion.model.Usuario;
import com.agroveterinaria.gestion.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.findByNombreUsuario("admin").isEmpty()) {

            Usuario admin = new Usuario();
            admin.setNombreUsuario("admin");

            admin.setContrasena(passwordEncoder.encode("admin123"));

            admin.setNombreCompleto("Administrador Principal");
            admin.setRol(Usuario.Rol.ADMIN);
            admin.setActivo(true);

            usuarioRepository.save(admin);

            System.out.println("---------------------------------------------");
            System.out.println(" USUARIO ADMIN CREADO (VERSIÓN ESPAÑOL)");
            System.out.println(" User: admin");
            System.out.println(" Pass: admin123");
            System.out.println(" Hash generado: " + admin.getContrasena());
            System.out.println("---------------------------------------------");
        }
    }
}