package com.alura.forohub.challengeforohub.domain.curso.dto;

import com.alura.forohub.challengeforohub.domain.curso.Categoria;

public record ActualizarCursoDTO(
        String name,
        Categoria categoria,
        Boolean activo) {
}
