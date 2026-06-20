CREATE TABLE detalleRecepcion(
    id INT AUTO_INCREMENT PRIMARY KEY,
    cantidad INT NOT NULL,
    estado VARCHAR(15) NOT NULL,
    ordenrecepcion_id INT NOT NULL,
    producto_id INT NOT NULL
);
