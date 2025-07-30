package com.example.foroHub.domain.topico;

import com.example.foroHub.domain.topico.DatosListadoTopico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface TopicoRepository extends JpaRepository<Topico, Long> {

    boolean existsByTituloAndMensaje(String titulo, String mensaje);

    @Query("""
        SELECT new com.example.foroHub.domain.topico.DatosListadoTopico(
            t.id, t.titulo, t.mensaje, t.fechaCreacion, t.estado, t.autor.nombre, t.curso.nombre
        )
        FROM Topico t
        WHERE (:nombreCurso IS NULL OR t.curso.nombre = :nombreCurso)
          AND (:anio IS NULL OR YEAR(t.fechaCreacion) = :anio)
        ORDER BY t.fechaCreacion ASC
    """)
    Page<DatosListadoTopico> buscarPorCursoYAnio(
            @Param("nombreCurso") String nombreCurso,
            @Param("anio") Integer anio,
            Pageable pageable
    );
}