package com.alura.forohub.challengeforohub.domain.topico.dto;

import com.alura.forohub.challengeforohub.domain.topico.Estado;

public record ActualizarTopicoDTO(
        String titulo,
        String mensaje,
        Estado estado,
        Long cursoId
) {
}
