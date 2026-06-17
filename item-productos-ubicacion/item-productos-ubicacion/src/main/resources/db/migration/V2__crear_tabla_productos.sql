CREATE TABLE productos(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    sku VARCHAR(50) NOT NULL UNIQUE,
    stock_actual INT NOT NULL,
    ubicacion_id INT NOT NULL,
    CONSTRAINT fk_productos_ubicacion
        FOREIGN KEY (ubicacion_id) REFERENCES ubicaciones(id)
        ON DELETE RESTRICT
);
