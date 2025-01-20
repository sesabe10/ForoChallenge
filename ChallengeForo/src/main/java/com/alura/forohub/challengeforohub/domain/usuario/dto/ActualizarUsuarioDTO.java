package com.alura.forohub.challengeforohub.domain.usuario.dto;

import com.alura.forohub.challengeforohub.domain.usuario.Role;

public record ActualizarUsuarioDTO(
        String password,
        Role role,
        String nombre,
        String apellido,
        String email,
        Boolean enabled
) {
}
