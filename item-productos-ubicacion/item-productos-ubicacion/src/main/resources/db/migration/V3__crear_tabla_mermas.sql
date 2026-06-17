CREATE TABLE mermas(
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha_reporte DATE NOT NULL,
    cantidad INT NOT NULL,
    motivo VARCHAR(255) NOT NULL,
    producto_id INT NOT NULL,
    FOREIGN KEY (producto_id) REFERENCES productos(id) ON DELETE CASCADE
);