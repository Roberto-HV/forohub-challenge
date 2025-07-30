package com.example.foroHub.controller;

import com.example.foroHub.domain.autor.AutorRepository;
import com.example.foroHub.domain.curso.CursoRepository;
import com.example.foroHub.domain.topico.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private AutorRepository autorRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<?> registrar(@RequestBody @Valid DatosRegistroTopico datos) {
        if (topicoRepository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje())) {
            return ResponseEntity.badRequest().body("Tópico duplicado: ya existe uno con el mismo título y mensaje");
        }

        var autor = autorRepository.findByNombre(datos.idAutor())
                .orElseThrow(() -> new IllegalArgumentException("Autor no encontrado"));

        var curso = cursoRepository.findByNombre(datos.idCurso())
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));

        var topico = new Topico(datos.titulo(), datos.mensaje(), autor, curso);
        topicoRepository.save(topico);

        return ResponseEntity.ok("Tópico registrado con éxito");
    }

    @GetMapping
    public Page<DatosListadoTopico> listar(@PageableDefault(size = 10, sort = "fechaCreacion") Pageable paginacion) {
        return topicoRepository.findAll(paginacion).map(topico -> new DatosListadoTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getEstado().toString(),
                topico.getAutor().getNombre(),
                topico.getCurso().getNombre()
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleTopico> obtenerDetalleTopico(@PathVariable Long id) {
        return topicoRepository.findById(id)
                .map(topico -> ResponseEntity.ok(new DatosDetalleTopico(topico)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarTopico(
            @PathVariable Long id,
            @RequestBody @Valid DatosActualizarTopico datos
    ) {
        Optional<Topico> topicoOptional = topicoRepository.findById(id);

        if (topicoOptional.isPresent()) {
            Topico topico = topicoOptional.get();
            topico.setTitulo(datos.titulo());
            topico.setMensaje(datos.mensaje());
            topicoRepository.save(topico);
            return ResponseEntity.ok(new DatosDetalleTopico(topico));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTopico(@PathVariable Long id) {
        Optional<Topico> topicoOptional = topicoRepository.findById(id);

        if (topicoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        topicoRepository.deleteById(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}