CREATE TABLE topicos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    mensaje TEXT NOT NULL,
    fechaCreacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'abierto',
    idAutor INT,
    idCurso INT,
    FOREIGN KEY (idAutor) REFERENCES usuarios(id),
    FOREIGN KEY (idCurso) REFERENCES cursos(id)
);