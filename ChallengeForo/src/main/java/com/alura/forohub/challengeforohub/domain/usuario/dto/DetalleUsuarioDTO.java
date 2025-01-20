package com.alura.forohub.challengeforohub.domain.usuario.dto;

import com.alura.forohub.challengeforohub.domain.usuario.Role;
import com.alura.forohub.challengeforohub.domain.usuario.Usuario;

public record DetalleUsuarioDTO(
        Long id,
        String username,
        Role role,
        String nombre,
        String apellido,
        String email,
        Boolean enabled
) {

    public DetalleUsuarioDTO(Usuario usuario){
        this(usuario.getId(),
                usuario.getUsername(),
                usuario.getRole(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getEnabled()
        );
    }
}
