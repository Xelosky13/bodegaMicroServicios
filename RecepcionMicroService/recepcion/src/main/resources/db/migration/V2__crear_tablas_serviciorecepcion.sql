CREATE TABLE proveedor(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre varchar(100) NOT NULL,
    rut varchar(10) NOT NULL,
    telefono varchar(9)NOT NULL,
    nombreContacto varchar(100)NOT NULL,
    ordenes_id INT NOT NULL
);


