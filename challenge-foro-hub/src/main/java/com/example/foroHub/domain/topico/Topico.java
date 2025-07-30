package com.example.foroHub.domain.topico;



import com.example.foroHub.domain.autor.Autor;
import com.example.foroHub.domain.curso.Curso;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "topicos", uniqueConstraints = @UniqueConstraint(columnNames = {"titulo", "mensaje"}))
@Getter
@NoArgsConstructor
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String mensaje;

    @ManyToOne
    @JoinColumn(name = "idAutor")
    private Autor autor;

    @ManyToOne
    @JoinColumn(name = "idCurso")
    private Curso curso;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(name = "status")
    private Boolean estado;

    public Topico(String titulo, String mensaje, Autor autor, Curso curso) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.autor = autor;
        this.curso = curso;
    }
}