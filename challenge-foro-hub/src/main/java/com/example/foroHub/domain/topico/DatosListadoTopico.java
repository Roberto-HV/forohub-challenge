package com.example.foroHub.domain.topico;

import java.time.LocalDateTime;

public record DatosListadoTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        String estado,  // Representación legible
        String nombreAutor,
        String nombreCurso
) {
    public DatosListadoTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getEstado() != null && topico.getEstado() ? "abierto" : "cerrado",
                topico.getAutor().getNombre(),
                topico.getCurso().getNombre()
        );
    }
}