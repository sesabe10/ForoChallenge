package com.alura.forohub.challengeforohub.domain.curso.dto;

import com.alura.forohub.challengeforohub.domain.curso.Categoria;
import com.alura.forohub.challengeforohub.domain.curso.Curso;

public record DetalleCursoDTO(
        Long id,
        String name,
        Categoria categoria,
        Boolean activo) {

    public DetalleCursoDTO(Curso curso){
        this(
                curso.getId(),
                curso.getName(),
                curso.getCategoria(),
                curso.getActivo());
    }

}
