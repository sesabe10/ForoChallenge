package com.alura.forohub.challengeforohub.domain.respuesta.validations.update;

import com.alura.forohub.challengeforohub.domain.respuesta.dto.ActualizarRespuestaDTO;

public interface ValidarRespuestaActualizada {

    void validate(ActualizarRespuestaDTO data, Long respuestaId);
}
