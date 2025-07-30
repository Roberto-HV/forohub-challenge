CREATE TABLE respuestas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    mensaje TEXT NOT NULL,
    idTopico INT,
    fechaCreacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    idAutor INT,
    solucion BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (idTopico) REFERENCES topicos(id),
    FOREIGN KEY (idAutor) REFERENCES usuarios(id)
);